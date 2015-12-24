package com.zn.main;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by zn on 2015/12/16.
 */
public class DataDAO {
    private static final String DB_PATH = "db";
    private static final String DB_FILE_PATH = DB_PATH + "/d";
    Logger logger = Logger.getLogger("DataDAO");
    private List<AccountEntity> datas = new ArrayList<AccountEntity>();
    static {
        File file = new File(DB_PATH);
        if(!file.exists()) {
            file.mkdirs();
        }
        file = new File(DB_FILE_PATH);
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public DataDAO() {
        loadAll();
    }
    private boolean write(String content) {
        File file = new File(DB_FILE_PATH);
        if(file.exists()) {
            file.delete();
        }
        RandomAccessFile out = null;
        try {
            out = new RandomAccessFile(DB_FILE_PATH, "rw");
            out.skipBytes((int) out.length());
            out.write(content.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    private String read() throws FileNotFoundException {
        RandomAccessFile out = null;
        String content = "";
        File file = new File(DB_FILE_PATH);
        if(!file.exists()) {
            throw new FileNotFoundException(DB_FILE_PATH + "文件不存在!无法完成读取操作！");
        }
        try {
            out = new RandomAccessFile(DB_FILE_PATH, "rw");
            byte[] by = new byte[(int) out.length()];
            out.read(by);
            content = new String(by);
            return content;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return content;
    }

    /**
     * 添加帐号
     * @param entity
     * 如果添加后想保存到磁盘，请调用writeAll方法
     */
    public void insert(AccountEntity entity) {
        datas.add(entity);
    }

    /**
     * 修改帐号信息
     * @param accountEntity
     * 如果修改后想保存，请调用writeAll方法
     */
    public void update(AccountEntity accountEntity) {
        for(AccountEntity e : datas) {
            if(e.getId().equals(accountEntity.getId())) {
                e.setAccount(accountEntity.getAccount());
                e.setPassword(accountEntity.getPassword());
                e.setRemark(accountEntity.getRemark());
                e.setWebsite(accountEntity.getRemark());
                break;
            }
        }
    }

    /**
     *将数据写入到磁盘，文件的位置为当前程序运行下的db/d文件
     */
    public void writeAll() {
        this.write(this.datas2String());
    }

    public List<AccountEntity> findAll() {
        return this.datas;
    }
    /**
     * 删除账户信息
     * @param accountEntity
     * 如果删除后想保存，请调用writeAll方法
     */
    public void delete(AccountEntity accountEntity) {
        Iterator<AccountEntity> iterable = datas.iterator();
        while(iterable.hasNext()) {
            AccountEntity entity = iterable.next();
            if(entity.getId().equals(accountEntity.getId())) {
                iterable.remove();
                break;
            }
        }
    }
    private void loadAll(){
        String content = null;
        try {
            content = this.read();
            String [] contents = content.split("\r\n");
            AccountEntity accountEntity = null;
            for(String c : contents) {
                accountEntity = new AccountEntity();
                accountEntity.convert2This(c);
                if(accountEntity.getId() != null) {
                    this.insert(accountEntity);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String datas2String() {
        StringBuffer buffer = new StringBuffer();
        for(AccountEntity entity : datas) {
            buffer.append(entity.toString() + "\r\n");
        }
        return buffer.toString();
    }
}
