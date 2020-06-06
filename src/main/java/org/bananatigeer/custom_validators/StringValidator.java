package org.bananatigeer.custom_validators;

import com.univocity.parsers.conversions.Validator;

public class StringValidator implements Validator<String>{
    @Override
    public String validate(String s) {
        if(s != null || !s.isEmpty()){
            for(char c : s.toCharArray()){
                if(!Character.isLetter(c)){
                    return "Value must be a letter only";
                }
            }
        }
        return null;
    }
}
