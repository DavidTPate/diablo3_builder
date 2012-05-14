
package com.pate.diablo.model;

import java.util.UUID;

public class ClassBuild
{

    private String className;
    private String name;
    private String url;
    private UUID   uuid = UUID.randomUUID();

    public ClassBuild(String name, String className, String url)
    {

        super();
        this.name = name;
        this.className = className;
        this.url = url;
    }

    public String getClassName()
    {

        return className;
    }

    public String getName()
    {

        return name;
    }

    public String getUrl()
    {

        return url;
    }

    public UUID getUuid()
    {

        return uuid;
    }
}
