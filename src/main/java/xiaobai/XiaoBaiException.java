package xiaobai;

class XiaoBaiException extends Exception {
    XiaoBaiException(String message) { super(message); }
}

class EmptyDescriptionException extends XiaoBaiException {
    EmptyDescriptionException(String cmd) {
        super("(ಠ_ಠ) The description of a " + cmd + " cannot be empty.");
    }
}

class UnknownCommandException extends XiaoBaiException {
    UnknownCommandException(String raw) {
        super("(・∀・*) I'm sorry, but I don't know what that means.");
    }
}

class InvalidIndexException extends XiaoBaiException {
    InvalidIndexException() {
        super("(˙_˙) That task number is invalid.");
    }
}

class InvalidFormatException extends XiaoBaiException {
    InvalidFormatException(String hint) {
        super("( ˘･з･) Invalid format. " + hint);
    }
}
