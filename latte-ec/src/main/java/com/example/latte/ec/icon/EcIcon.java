package com.example.latte.ec.icon;

import com.joanzapata.iconify.Icon;

public enum  EcIcon implements Icon {
    icon_scan('\ue602'),
    icon_ali_pay('\ue606');


    private char character;

    EcIcon(char character) {
        this.character = character;
    }

    @Override
    public String key() {
        return name().replace('_', '-');
    }

    @Override
    public char character() {
        return character;
    }
}
