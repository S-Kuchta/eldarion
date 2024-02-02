package kuchtastefan.hint;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Hint {
    private final String text;
    private boolean showed;

    public Hint( String text) {
        this.text = text;
        this.showed = false;
    }
}
