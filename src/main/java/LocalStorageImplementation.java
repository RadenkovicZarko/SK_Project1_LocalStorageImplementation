import java.io.File;
import java.util.HashMap;
import java.util.List;

public class LocalStorageImplementation implements StorageSpecification {

  public static void main(String[] args) {

  }

  @Override
  public void createRootFolder() {

  }

  @Override
  public void createRootFolder(Configuration configuration) {

  }

  @Override
  public void createFolderOnSpecifiedPath(String s) {

  }

  @Override
  public void putFileOnSpecifiedPath(List<File> list, String s) {

  }

  @Override
  public void deleteFileOrDirectory(String s) {

  }

  @Override
  public void deleteFileOrDirectory(String s, String s1) {

  }

  @Override
  public void moveFileFromDirectoryToAnother(String s, String s1, String s2) {

  }

  @Override
  public void downloadFileOrDirectory(String s, String s1) {

  }

  @Override
  public void renameFileOrDirectory(String s, String s1, String s2) {

  }

  @Override
  public HashMap<String, FileMetadata> filesFromDirectory(String s) {
    return null;
  }

  @Override
  public HashMap<String, FileMetadata> filesFromChildrenDirectory(String s) {
    return null;
  }

  @Override
  public HashMap<String, FileMetadata> allFilesFromDirectoryAndSubdirectory(String s) {
    return null;
  }

  @Override
  public HashMap<String, String> filesFromDirectoryExt(String s, List<String> list) {
    return null;
  }

  @Override
  public HashMap<String, String> filesFromChildrenDirectoryExt(String s, List<String> list) {
    return null;
  }

  @Override
  public HashMap<String, String> allFilesFromDirectoryAndSubdirectoryExt(String s, List<String> list) {
    return null;
  }

  @Override
  public HashMap<String, String> filesFromDirectorySubstring(String s, String s1) {
    return null;
  }

  @Override
  public String folderNameByFileName(String s) {
    return null;
  }

  @Override
  public List<String> returnFilesInDateInterval(String s, Date date, Date date1) {
    return null;
  }
}
