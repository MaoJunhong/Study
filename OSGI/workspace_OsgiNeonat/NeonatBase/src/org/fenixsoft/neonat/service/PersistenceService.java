package org.fenixsoft.neonat.service;

import org.fenixsoft.neonat.entity.Board;

/**
 * 论坛持久化服务<br>
 * 为了简便，这里暂不考虑太多实用性，有一点变动都持久化整个Board对象，真正的BBS的持久化不可能这样做。
 * 
 * @author IcyFenix
 */
public interface PersistenceService {

	/**
	 * 保存版面信息变动情况
	 * 
	 * @param board
	 */
	void save(Board board);

	/**
	 * 加载版面信息
	 * 
	 * @param board
	 */
	void load(Board board);
}
