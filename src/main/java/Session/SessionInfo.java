package Session;

import Encryption.AESCipher;

import javax.crypto.spec.SecretKeySpec;

public class SessionInfo {
    private SecretKeySpec masterPassword;

    public SessionInfo(String masterPassword)
    {
        this.masterPassword = AESCipher.generateKey(masterPassword);
    }

    public SecretKeySpec getMasterPassword()
    {
        return this.masterPassword;
    }
}
