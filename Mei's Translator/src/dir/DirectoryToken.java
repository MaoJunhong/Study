package dir;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import translate.Translator;

public class DirectoryToken {
	private String srcPos = null;
	private String dstPos = null;

	private List<String> files;
	private int currentIdx;

	public DirectoryToken() {
		files = new LinkedList<String>();
		currentIdx = -1;
	}

	public void translateAll(String from, String to) {
		Iterator<String> it = files.iterator();

		while (it.hasNext()) {
			String file = it.next();
			String src = read(file);
			String dst = Translator.Translate(from, to, src);
			++currentIdx;
			save(dst);
		}
	}

	public String getNextFile() {
		++currentIdx;
		if (currentIdx > files.size() - 1) {
			currentIdx = files.size() - 1;
		}
		return read(files.get(currentIdx));
	}

	public String getCurrentFile() {
		if (currentIdx < 0 || currentIdx > files.size() - 1) {
			return "";
		}
		return read(files.get(currentIdx));
	}

	public String getLastFile() {
		--currentIdx;
		if (currentIdx < 0) {
			currentIdx = 0;
		}
		return read(files.get(currentIdx));
	}

	public String getCurrentFileName() {
		if (currentIdx < 0 || currentIdx > files.size() - 1) {
			return "";
		}
		return files.get(currentIdx);
	}

	public String getSrcPos() {
		return srcPos;
	}

	public void setSrcPos(String srcPos) {
		this.srcPos = srcPos;
		token(new File(srcPos));
		currentIdx = 0;
	}

	public String getDstPos() {
		return dstPos;
	}

	public void setDstPos(String dstPos) {
		this.dstPos = dstPos;
	}

	public void save(String content) {
		if (currentIdx == -1) {
			return;
		}
		String cfile = files.get(currentIdx);
		// System.out.println(cfile);
		String fileName = cfile.substring(cfile.lastIndexOf("\\"));
		String path = srcPos;
		if (dstPos != null) {
			path = dstPos;
		} else {
			path = cfile.substring(0, cfile.lastIndexOf("\\"));
			fileName = "cn.txt";
		}

		File file = new File(path + "/" + fileName);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			fos.write(content.getBytes());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void token(File file) {
		File[] cfiles = file.listFiles();

		if (cfiles == null) {
			return;
		}

		for (File cfile : cfiles) {
			if (cfile.isFile()) {
				String absPath = cfile.getAbsolutePath();
				if (absPath.endsWith(".txt")) {
					// System.out.println(absPath);
					files.add(cfile.getAbsolutePath());
				}
			} else {
				token(cfile);
			}
		}
	}

	public String read(String fileName) {
		File file = new File(fileName);
		byte[] content = new byte[(int) file.length()];

		FileInputStream in = null;
		try {
			in = new FileInputStream(file);
			in.read(content);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return new String(content);
	}

	public static void main(String[] args) {
		DirectoryToken token = new DirectoryToken();
		// token.setSrcPos("D:\\UserSwrd\\Desktop\\a");
		token.setSrcPos("D:\\UserSwrd\\Desktop\\bathrooms‘° “5");
		// token.setDstPos("D:\\UserSwrd\\Desktop\\a");
		// token.translateAll("en", "zh");

	}
}
