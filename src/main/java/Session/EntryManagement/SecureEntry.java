package Session.EntryManagement;

import Session.EntryManagement.Enum.EEntryType;

import java.io.Serializable;
import java.util.ArrayList;

public class SecureEntry implements Serializable {
    private String name;
    private String algorithmName;
    private EEntryType type;

    private boolean encryptable;
    private boolean isEncrypted;

    private ArrayList<String> entries;

    public SecureEntry(String name, String algorithmName)
    {
        this.name = name;
        this.algorithmName = algorithmName;
        this.entries = new ArrayList<String>();
    }

    public SecureEntry(String name, String algorithmName, String[] itemNames, EEntryType type)
    {
        this(name, algorithmName);

        this.type = type;

        /**
         * Initialize the entries
         * */
        for (String itemName : itemNames)
            entries.add(itemName);
    }

    public String getName()
    {
        return this.name;
    }

    public EEntryType getType()
    {
        return this.type;
    }

    public String[] getItems()
    {
        String[] toReturn = new String[this.entries.size()];

        for (int i = 0; i < this.entries.size(); i++)
        {
            toReturn[i] = this.entries.get(i);
        }

        return toReturn;
    }
}
