package org.fenixsoft.neonat.service;

import org.fenixsoft.neonat.entity.Board;

/**
 * Neonat BBS�ĵ�ģ�ͷ��ʷ���ʵ����
 * 
 * @author IcyFenix
 */
public class NeonatModelServiceImpl implements NeonatModelService {

	private volatile static Board board;

	@Override
	public Board getBoard() {
		if (board == null) {
			synchronized (Board.class) {
				if (board == null) {
					board = new Board("Neonat BBS");
					board.load();
				}
			}
		}
		return board;
	}

	@Override
	public void updateBoard(Board board) {
		if (board == null) {
			throw new IllegalArgumentException("�����˿յĲ���Board");
		}
		board.save();
		NeonatModelServiceImpl.board = board;
	}

}
