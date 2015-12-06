package messages;

import java.io.Serializable;

/**
 * Created by maciek on 28.11.15.
 */
public class NotifyAboutActionMsg implements Serializable {

    @Override
    public boolean equals(Object o)
    {
        // using in tests
        return o instanceof NotifyAboutActionMsg;
    }
}
