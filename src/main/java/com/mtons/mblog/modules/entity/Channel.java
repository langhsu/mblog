/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.modules.entity;

import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 模块/内容分组
 * @author langhsu
 *
 */
@Data
@Entity
@Table(name = "mto_channel")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Channel implements Serializable {
	private static final long serialVersionUID = 2436696690653745208L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	/**
	 * 名称
	 */
	@Column(length = 32)
	private String name;

	/**
	 * 唯一关键字
	 */
	@Column(name = "key_", unique = true, length = 32)
	private String key;

	private int status;

}
