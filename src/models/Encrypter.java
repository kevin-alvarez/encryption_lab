package models;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Encrypter{

  private class BlockEncryptor implements Callable<int[]> {
    private int[] _block;
    private int[] _keys;

    public BlockEncryptor(int[] block, int[] keys) {
      _block = block;
      _keys = keys;
    }

    @Override
    public int[] call() {
      _block = encryptBlock(_block, _keys);
      return _block;
    }

  }

  private class BlockDecryptor implements Callable<int[]> {
    private int[] _block;
    private int[] _keys;

    public BlockDecryptor(int[] block, int[] keys) {
      _block = block;
      _keys = keys;
    }

    @Override
    public int[] call() {
      _block = decryptBlock(_block, _keys);
      return _block;
    }
  }

  public Encrypter(){
    key = 155;
  }

  private int key;

  public void beginEncryption(String plainText, int blockSize, int iterations) throws java.io.IOException, java.lang.InterruptedException, java.util.concurrent.ExecutionException {
    int[] keys = createKeys(key, iterations);
    byte[] textBinary = plainText.getBytes("UTF-8");
    int[][] blocks = createBlocks(textBinary, blockSize);
    encrypt(blocks, keys);
    byte[] encryptedText = joinBlocks(blocks, blockSize);
    String encryptedMessage = new String(encryptedText, "UTF-8"); // Mensaje Encriptado
    decrypt(blocks, keys);
    byte[] decodedTextBinary = joinBlocks(blocks, blockSize);
    String decodedText = new String(decodedTextBinary, "UTF-8"); // Mensaje Desencriptado
  }

  private int[] createKeys(int key, int iterations) {
    int[] keys = new int[iterations];
    keys[0] = key;
    for (int i = 1; i < iterations; i++) {
      key = (key + (key / 2) ^ 2) % 256; // Modificar esto.
      keys[i] = key;
    }
    return keys;
  }

  private int[][] createBlocks(byte[] textBinary, int blockSize) {
    int margin = blockSize / 8;
    int rows;
    if ((textBinary.length % margin) == 0) {
      rows = textBinary.length / margin;
    } else {
      rows = (textBinary.length / margin) + 1;
    }
    int[][] blocks = new int[rows][margin];
    int i = 0;
    while(i < textBinary.length) {
      blocks[i / margin][i % margin] = textBinary[i];
      i++;
    }
    return blocks;
  }

  private void encrypt(int[][] blocks, int[] keys) throws java.lang.InterruptedException, java.util.concurrent.ExecutionException {
    ExecutorService threadPool = Executors.newFixedThreadPool(blocks.length);
    for (int i = 0; i < blocks.length; i++) {
      Callable<int[]> process = new BlockEncryptor(blocks[i], keys);
      Future<int[]> result = threadPool.submit(process);
      blocks[i] = result.get();
    }
    threadPool.shutdown();
  }

  private int[] encryptBlock(int[] block, int[] keys) {
    int[] left = new int[block.length / 2];
    int[] right = new int[block.length / 2];
    int i;
    for (i = 0; i < block.length / 2; i++) {
      left[i] = block[i];
    }
    for (i = block.length / 2; i < block.length; i++) {
      right[i - block.length / 2] = block[i];
    }
    i = 0;
    int j;
    while (i < keys.length) { // hay que cambiar algo aquí
      int[] rightXOR = new int[right.length];
      System.arraycopy(right, 0, rightXOR, 0, right.length);
      int[] newRight = new int[right.length];
      for (j = 0; j < rightXOR.length; j++) {
        rightXOR[j] = rightXOR[j] ^ keys[i];
      }
      for (j = 0; j < rightXOR.length; j++) {
        newRight[j] = left[j] ^ rightXOR[j];
      }
      left = right;
      right = newRight;
      i++;
    }
    for (i = 0; i < block.length / 2; i++) {
      block[i] = left[i];
    }
    for (i = block.length / 2; i < block.length; i++) {
      block[i] = right[i - block.length / 2];
    }
    return block;
  }

  private void decrypt(int[][] blocks, int[] keys) throws java.lang.InterruptedException, java.util.concurrent.ExecutionException {
    ExecutorService threadPool = Executors.newFixedThreadPool(blocks.length);
    for (int i = 0; i < blocks.length; i++) {
      Callable<int[]> process = new BlockDecryptor(blocks[i], keys);
      Future<int[]> result = threadPool.submit(process);
      blocks[i] = result.get();
    }
    threadPool.shutdown();
  }

  private int[] decryptBlock(int[] block, int[] keys) {
    int[] left = new int[block.length / 2];
    int[] right = new int[block.length / 2];
    int i;
    for (i = 0; i < block.length / 2; i++) {
      left[i] = block[i];
    }
    for (i = block.length / 2; i < block.length; i++) {
      right[i - block.length / 2] = block[i];
    }
    i = 0;
    int j;
    while (i < keys.length) { // hay que cambiar algo aquí
      int[] leftXOR = new int[left.length];
      System.arraycopy(left, 0, leftXOR, 0, left.length);
      int[] newLeft = new int[left.length];
      for (j = 0; j < leftXOR.length; j++) {
        leftXOR[j] = leftXOR[j] ^ keys[keys.length - 1 - i];
      }
      for (j = 0; j < leftXOR.length; j++) {
        newLeft[j] = right[j] ^ leftXOR[j];
      }
      right = left;
      left = newLeft;
      i++;
    }
    for (i = 0; i < block.length / 2; i++) {
      block[i] = left[i];
    }
    for (i = block.length / 2; i < block.length; i++) {
      block[i] = right[i - block.length / 2];
    }
    return block;
  }

  private byte[] joinBlocks(int[][] blocks, int blockSize) {
    int margin = blockSize / 8;
    int quantity = 0;
    for (int i = 0; i < blocks.length; i++) {
      for (int j = 0; j < margin; j++) {
        if (blocks[i][j] == 0) {
          break;
        }
        quantity++;
      }
    }
    byte[] textBinary = new byte[quantity];
    for (int i = 0; i < quantity; i++) {
      textBinary[i] = (byte)(blocks[i / margin][i % margin]);
    }
    return textBinary;
  }
}
