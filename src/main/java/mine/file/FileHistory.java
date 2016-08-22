package mine.file;

import java.io.File;

import mine.MineException;
import mine.MineUtils;
import mine.util.History;

/**
 * ファイルの履歴を管理する。<p>
 *
 * @author k-saito
 */
public class FileHistory {

	private History<String> history;

	/**
	 * コンストラクタ<p>
	 *
	 * @param historyFile // 履歴を保存するファイル。
	 * @param max // 履歴の最大数
	 */
	public FileHistory(int max){
		this.history = new History<String>(max);
	}

	public void load(String historyFile){
		if (!new File(historyFile).exists()) {
			System.out.println("HistoryFile is not found. [" + new File(historyFile).getAbsolutePath() + "]");
			return;
		}
		try {
			setFileList(MineUtils.readStringArray(historyFile));
		} catch (MineException e) {
			e.printStackTrace();
		}
	}

	public void save(String historyFile) {
		try {
			MineUtils.writeStringArray(historyFile, getFileList());
		} catch (MineException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 指定された番号のファイルを返す。<p>
	 *
	 * @param n
	 * @return n番目のファイル
	 */
	public String getFile(int n) {
		return history.get(n);
	}

	/**
	 * 最新のファイルを返す。<p>
	 *
	 * @return
	 */
	public String getCurrentFile(){
		return getFile(0);
	}

	/**
	 * ファイルを追加する。<p>
	 *
	 * @param file
	 */
	public void addFile(String file) {
		history.add(file);
	}

	/**
	 * @return ファイルのリスト
	 */
	public String[] getFileList() {
		return history.toArray(new String[0]);
	}

	/**
	 * @param list
	 */
	public void setFileList(String[] list) {
		history.reset(list);
	}

	public int size() {
		return history.size();
	}
}
