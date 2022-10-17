package praktikum;

import org.apache.commons.lang3.RandomStringUtils;

public class GenerationUsers {
    public static CreateUsers getRandomUsers(){
        return new CreateUsers(
                RandomStringUtils.randomAlphanumeric(10) + "@" + RandomStringUtils.randomAlphanumeric(10) + "." + RandomStringUtils.randomAlphanumeric(10),
                RandomStringUtils.randomAlphanumeric(10),
                RandomStringUtils.randomAlphanumeric(10)
        );
    }

    public static CreateUsers getRandomWithoutEmail(){
        return new CreateUsers(
                RandomStringUtils.randomAlphanumeric(0),
                RandomStringUtils.randomAlphanumeric(10),
                RandomStringUtils.randomAlphanumeric(10)
        );
    }

    public static CreateUsers getRandomWithoutPassword(){
        return new CreateUsers(
                RandomStringUtils.randomAlphanumeric(10) + "@" + RandomStringUtils.randomAlphanumeric(10) + "." + RandomStringUtils.randomAlphanumeric(10),
                RandomStringUtils.randomAlphanumeric(0),
                RandomStringUtils.randomAlphanumeric(10)
        );
    }

    public static CreateUsers getRandomWithoutName(){
        return new CreateUsers(
                RandomStringUtils.randomAlphanumeric(10),
                RandomStringUtils.randomAlphanumeric(10),
                RandomStringUtils.randomAlphanumeric(0)
        );
    }
}
