package com.enib.cai.vertx.services.impl;

import com.enib.cai.vertx.services.Files;
import com.mongodb.DB;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;

import javax.inject.Inject;
import java.io.ByteArrayOutputStream;

/**
 * User: lambour
 * Date: 01/11/14 23:45
 * Copyright: Cityzen Data
 */
public class FIlesImpl implements Files {
  public byte[] get(String name) {
    try {
      GridFS gfsPhoto = new GridFS(db, "img");
      GridFSDBFile imageForOutput = gfsPhoto.findOne(name);

      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      imageForOutput.writeTo(bos);
      return bos.toByteArray();
    } catch( Exception exp) {

    }
    return null;
  }

  @Inject
  private DB db;
}
