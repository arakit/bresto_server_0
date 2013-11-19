package jp.crudefox.server.ricoh.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

public class Secure {


	private static Random sRandom = new Random(System.currentTimeMillis());
	public static final int randomInt(int n){
		return sRandom.nextInt(n);
	}

	public static final String createRandomAlphabet(int str_length){

		if(str_length<=0) return null;

		SecureRandom secRandom = null;
    	byte bytes[] = new byte[16];
        try {
            secRandom = SecureRandom.getInstance("SHA1PRNG");
            secRandom.nextBytes(bytes);
        } catch (NoSuchAlgorithmException e) {
        	System.out.println("そんなアルゴリズムはないよ");
        	return null;
        }

        StringBuilder sb = new StringBuilder();
        for(int i=0;i<str_length;i++){
        	int r = secRandom.nextInt(26*2);
        	if(r<26) 	sb.append((char)('a'+r));
        	else		sb.append((char)('A'+(r-26)));
        }

        return sb.toString();
	}

}
