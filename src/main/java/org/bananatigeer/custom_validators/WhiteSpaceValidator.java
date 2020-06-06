package org.bananatigeer.custom_validators;

import com.univocity.parsers.conversions.Validator;

public class WhiteSpaceValidator implements Validator<String> {

    @Override
    public String validate(String s) {
        if(s != null){
            for(int i = 0; i < s.length(); i++){
                if(Character.isWhitespace(s.charAt(i))){
                    return "Has whitespace";
                }
            }
        }
        return null;
    }
}
