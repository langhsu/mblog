/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.base.upload.impl;

import com.mtons.mblog.base.context.AppContext;
import com.mtons.mblog.base.utils.FileKit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @author langhsu
 * @since  3.0
 */
@Slf4j
@Component
public class NativeFileRepoImpl extends AbstractFileRepo {
	@Autowired
	private AppContext appContext;

	@Override
	public void deleteFile(String storePath) {
		File file = new File(appContext.getRoot() + storePath);

		// 文件存在, 且不是目录
		if (file.exists() && !file.isDirectory()) {
			file.delete();
			log.info("fileRepo delete " + storePath);
		}
	}

	@Override
	public String writeToStore(byte[] bytes, String pathAndFileName) throws Exception {
		String dest = appContext.getRoot() + pathAndFileName;
		FileKit.writeByteArrayToFile(bytes, dest);
		return pathAndFileName;
	}

}
