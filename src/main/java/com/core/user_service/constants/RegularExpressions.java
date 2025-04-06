package com.core.user_service.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class RegularExpressions {

  public static final String PASSWORD_FORMAT = "^(?=[^A-Z]*[A-Z][^A-Z]*$)(?=(?:\\D*\\d){2}\\D*$)[a-zA-Z\\d]{8,12}$";

  public static final String EMAIL_FORMAT = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}";
}
