package com.yeetrack.yinyueyun;

/**
 * Created with IntelliJ IDEA.
 * User: victor
 * Date: 13-10-23
 * Time: 下午7:31
 * To change this template use File | Settings | File Templates.
 */
public class SongInfoEntity
{
    private String errorCode;
    private DataEntity data;

    public String getErrorCode()
    {
        return errorCode;
    }

    public DataEntity getData()
    {
        return data;
    }

    public void setErrorCode(String errorCode)
    {
        this.errorCode = errorCode;
    }

    public void setData(DataEntity data)
    {
        this.data = data;
    }
}
