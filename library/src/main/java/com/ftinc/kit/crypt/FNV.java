/*
 * Copyright (c) 2015 52inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ftinc.kit.crypt;

import java.math.BigInteger;

public class FNV {
  private static final BigInteger INIT32  = new BigInteger("811c9dc5",         16);
  private static final BigInteger INIT64  = new BigInteger("cbf29ce484222325", 16);
  private static final BigInteger PRIME32 = new BigInteger("01000193",         16);
  private static final BigInteger PRIME64 = new BigInteger("100000001b3",      16);
  private static final BigInteger MOD32   = new BigInteger("2").pow(32);
  private static final BigInteger MOD64   = new BigInteger("2").pow(64);

  public BigInteger fnv1_32(byte[] data) {
    BigInteger hash = INIT32;

    for (byte b : data) {
      hash = hash.multiply(PRIME32).mod(MOD32);
      hash = hash.xor(BigInteger.valueOf((int) b & 0xff));
    }

    return hash;
  }

  public BigInteger fnv1_64(byte[] data) {
    BigInteger hash = INIT64;

    for (byte b : data) {
      hash = hash.multiply(PRIME64).mod(MOD64);
      hash = hash.xor(BigInteger.valueOf((int) b & 0xff));
    }

    return hash;
  }

  public BigInteger fnv1a_32(byte[] data) {
    BigInteger hash = INIT32;

    for (byte b : data) {
      hash = hash.xor(BigInteger.valueOf((int) b & 0xff));
      hash = hash.multiply(PRIME32).mod(MOD32);
    }

    return hash;
  }

  public BigInteger fnv1a_64(byte[] data) {
    BigInteger hash = INIT64;

    for (byte b : data) {
      hash = hash.xor(BigInteger.valueOf((int) b & 0xff));
      hash = hash.multiply(PRIME64).mod(MOD64);
    }

    return hash;
  }
}
