/**  
   Copyright 2008 University of Rochester

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/  

package edu.ur.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.apache.commons.codec.binary.Base64;

/**
 * Class for generating tokens
 * 
 * @author Sharmila Ranganathan
 *
 */
public class TokenGenerator
{

    /**
     * Generates and returns the token
     * 
     * @return the generated token
     */
    public static String getToken()
    {
    	 byte[] bytes = new byte[32];
    	 try {
			SecureRandom.getInstance("SHA1PRNG").nextBytes(bytes);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		String token = Base64.encodeBase64URLSafeString(bytes).substring(0, 32);
        return token;
    }
}
