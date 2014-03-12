package com.yeetrack.yinyueyun;

/**
 * Created with IntelliJ IDEA.
 * User: victor
 * Date: 13-10-23
 * Time: 下午7:18
 * To change this template use File | Settings | File Templates.
 * 音乐实体类
 */
public class SongEntity
{
    private String queryId;
    private String songId;
    private String songName;
    private String artistId;
    private String artistName;
    private String albumId;
    private String albumName;
    private String songPicSmall;
    private String songPicBig;
    private String songPicRadio;
    private String relateStatus;
    private String resourceType;
    private String del_status;
    private String fchar;
    private String allRate;
    private String distribution;

    public String getQueryId()
    {
        return queryId;
    }

    public String getSongId()
    {
        return songId;
    }

    public String getSongName()
    {
        return songName;
    }

    public String getArtistId()
    {
        return artistId;
    }

    public String getArtistName()
    {
        return artistName;
    }

    public String getAlbumId()
    {
        return albumId;
    }

    public String getAlbumName()
    {
        return albumName;
    }

    public String getSongPicSmall()
    {
        return songPicSmall;
    }

    public String getSongPicBig()
    {
        return songPicBig;
    }

    public String getSongPicRadio()
    {
        return songPicRadio;
    }

    public String getRelateStatus()
    {
        return relateStatus;
    }

    public String getResourceType()
    {
        return resourceType;
    }

    public String getDel_status()
    {
        return del_status;
    }

    public String getFchar()
    {
        return fchar;
    }

    public String getAllRate()
    {
        return allRate;
    }

    public String getDistribution()
    {
        return distribution;
    }

    public void setAlbumName(String albumName)
    {
        this.albumName = albumName;
    }

    public void setQueryId(String queryId)
    {
        this.queryId = queryId;
    }

    public void setSongId(String songId)
    {
        this.songId = songId;
    }

    public void setSongName(String songName)
    {
        this.songName = songName;
    }

    public void setArtistId(String artistId)
    {
        this.artistId = artistId;
    }

    public void setArtistName(String artistName)
    {
        this.artistName = artistName;
    }

    public void setAlbumId(String albumId)
    {
        this.albumId = albumId;
    }

    public void setSongPicSmall(String songPicSmall)
    {
        this.songPicSmall = songPicSmall;
    }

    public void setSongPicBig(String songPicBig)
    {
        this.songPicBig = songPicBig;
    }

    public void setSongPicRadio(String songPicRadio)
    {
        this.songPicRadio = songPicRadio;
    }

    public void setRelateStatus(String relateStatus)
    {
        this.relateStatus = relateStatus;
    }

    public void setResourceType(String resourceType)
    {
        this.resourceType = resourceType;
    }

    public void setDel_status(String del_status)
    {
        this.del_status = del_status;
    }

    public void setFchar(String fchar)
    {
        this.fchar = fchar;
    }

    public void setAllRate(String allRate)
    {
        this.allRate = allRate;
    }

    public void setDistribution(String distribution)
    {
        this.distribution = distribution;
    }
}
