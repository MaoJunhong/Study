package org.fenixsoft.neonat.service;

import org.fenixsoft.neonat.entity.Board;

/**
 * Neonat BBS�ĵ�ģ�ͷ��ʷ��񣬴ӱ������з��ʰ��桢���ӡ��û�������
 * 
 * @author IcyFenix
 */
public interface NeonatModelService {

	/**
	 * ��ȡ������Ϣ
	 */
	Board getBoard();

	/**
	 * ���°�����Ϣ���ı����������ݣ���������BBS����
	 */
	void updateBoard(Board board);
}
