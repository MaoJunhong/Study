package org.fenixsoft.neonat.persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.fenixsoft.neonat.entity.Board;
import org.fenixsoft.neonat.service.PersistenceService;

/**
 * 通过对象序列化来实现的持久化服务
 * 
 * @author IcyFenix
 */
public class SerializePersistenceService implements PersistenceService {

	private File getDataFile() {
		return Activator.getContext().getDataFile("neobat.bin");
	}

	@Override
	public void save(Board board) {
		ObjectOutputStream output;
		File dataFile = getDataFile();
		try {
			if (!dataFile.exists()) {
				dataFile.createNewFile();
			}
			output = new ObjectOutputStream(new FileOutputStream(dataFile));
			output.writeObject(board);
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void load(Board board) {
		File dataFile = getDataFile();
		if (dataFile.exists()) {
			ObjectInputStream input;
			try {
				input = new ObjectInputStream(new FileInputStream(dataFile));
				Board _board = (Board) input.readObject();
				board.setName(_board.getName());
				board.setTopics(_board.getTopics());
				board.setTopicsMap(_board.getTopicsMap());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
