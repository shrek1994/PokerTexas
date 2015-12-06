package messages;

import java.io.Serializable;

/**
 * Created by maciek on 28.11.15.
 */
public class BlindMsg implements Serializable {
    private int value;

    public int getValue() {
        return value;
    }

    public BlindMsg(int value) {

        this.value = value;
    }

    @Override
    public boolean equals(Object o)
    {
        // using in tests
        if ( o instanceof BlindMsg)
        {
            BlindMsg msg = (BlindMsg)o;
            return msg.value == value;
        }
        return false;
    }
}
