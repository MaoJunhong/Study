package org.fenixsoft.neonat.service;

import org.fenixsoft.neonat.entity.Board;

/**
 * ��̳�־û�����<br>
 * Ϊ�˼�㣬�����ݲ�����̫��ʵ���ԣ���һ��䶯���־û�����Board����������BBS�ĳ־û���������������
 * 
 * @author IcyFenix
 */
public interface PersistenceService {

	/**
	 * ���������Ϣ�䶯���
	 * 
	 * @param board
	 */
	void save(Board board);

	/**
	 * ���ذ�����Ϣ
	 * 
	 * @param board
	 */
	void load(Board board);
}
