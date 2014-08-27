package org.fenixsoft.neonat.service;

import org.fenixsoft.neonat.entity.Board;

/**
 * Neonat BBS的的模型访问服务，从本服务中访问版面、帖子、用户等数据
 * 
 * @author IcyFenix
 */
public interface NeonatModelService {

	/**
	 * 获取版面信息
	 */
	Board getBoard();

	/**
	 * 更新版面信息，改变了帖子数据，保存整个BBS，囧
	 */
	void updateBoard(Board board);
}
