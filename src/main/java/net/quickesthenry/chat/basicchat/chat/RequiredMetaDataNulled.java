package net.quickesthenry.chat.basicchat.chat;

public class RequiredMetaDataNulled extends Exception {
    public RequiredMetaDataNulled(String target) {
        super(target + " was required by configuration but provided as null.");
    }
}
