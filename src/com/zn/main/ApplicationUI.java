package com.zn.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by zn on 2015/12/16.
 * 程序UI
 */
public class ApplicationUI {
    private DataDAO dao = new DataDAO();
    private String login = "zn";
    private String password = "zhangning.holley_accp";
    private Scanner scanner = new Scanner(System.in);
    private List<AccountEntity> currentDatas = null;
    private boolean isLogin = false;
    private void menu() {
        String menu = "3.查询所有   4.模糊查询  5.添加    6.修改    7.退出";
        showMessageLine(menu);
    }
    private void login() {
        while(true) {
            showMessageLine("输入用户名和密码进行登录");
            String newLogin = scanner.next();
            String newPassword = scanner.next();
            if (newLogin.equals(login) && newPassword.equals(password)) {
                showMessageLine("登录成功!");
                isLogin = true;
                return;
            } else {
                showMessage("用户名或密码错误，请重新");
                isLogin = false;
            }
        }
    }
    private void findPassword() {
        showMessageLine("请输入登录帐号：");
        String login = scanner.next();
        if(login.equals(this.login)) {
            showMessageLine("您的密码是：" + password);
        } else {
            showMessageLine("盗取帐号可不好！一边凉快去吧。程序退出.");
            exit();
        }

    }
    private void findAllDatas() {
        currentDatas = dao.findAll();
        showList(currentDatas);
    }
    private void findFuzzy() {
        showMessageLine("请输入您要搜索的关键字");
        String key =scanner.next();
        List<AccountEntity> datas = dao.findAll();
        List<AccountEntity> tmp = new ArrayList<AccountEntity>();
        for(AccountEntity entity : datas) {
            if(entity.getAccount().contains(key)
                    || entity.getWebsite().contains(key)
                    || entity.getRemark().contains(key)){
                tmp.add(entity);
            }
        }
        currentDatas = tmp;
        showList(currentDatas);
    }
    private void add() {
        showMessageLine("请输入website：");
        String website = scanner.next();
        showMessageLine("请输入account：");
        String account = scanner.next();
        showMessageLine("请输入password：");
        String password = scanner.next();
        showMessageLine("请输入remark:使用[]作为开始和结束：");
        String remark = scanner.next();
        AccountEntity entity = new AccountEntity();
        entity.setRemark(remark);
        entity.setPassword(password);
        entity.setAccount(account);
        entity.setWebsite(website);
        dao.insert(entity);
        dao.writeAll();
    }

    private void modify() {
        while(true) {
            showMessageLine("请输入您要修改的序号:");
            int index = scanner.nextInt();
            if (index < 0 || index > currentDatas.size() - 1) {
                showMessage("序号错误，");
            } else {
                AccountEntity entity = currentDatas.get(index);
                showMessageLine("请输入website：如果保持原值请输入y");
                String website = scanner.next();
                showMessageLine("请输入account：如果保持原值请输入y");
                String account = scanner.next();
                showMessageLine("请输入password：如果保持原值请输入y");
                String password = scanner.next();
                showMessageLine("请输入remark:使用[]作为开始和结束：如果保持原值请输入y");
                String remark = scanner.next();
                if(!website.equals("y")) {
                    entity.setWebsite(website);
                }
                if(!account.equals("y")) {
                    entity.setAccount(account);
                }
                if(!password.equals("y")) {
                    entity.setPassword(password);
                }
                if(!remark.equals("y")) {
                    entity.setRemark(remark);
                }
                break;
            }
        }
        dao.writeAll();
    }

    private void exit() {
        showMessageLine("程序退出....");
        System.exit(1);
    }

    private void showMessage(String msg) {
        System.out.print(msg);
    }
    private void showMessageLine(String msg) {
        System.out.println(msg);
    }

    private void showList(List<AccountEntity> datas) {
        showMessageLine("序号---网站---帐号---密码---备注---id");
        for(int i = 0; i < datas.size(); i ++) {
            AccountEntity entity = datas.get(i);
            String id = entity.getId();
            String website = entity.getWebsite();
            String account = entity.getAccount();
            String password = entity.getPassword();
            String remark = entity.getRemark();
            //showMessageLine(i + "\t" + id + "\t\t\t" + website + "\t\t" + account + "\t\t\t\t\t" + password + "\t\t\t" + remark);
            showMessageLine(i+":" + entity.toString());
        }
    }
    public void main() {
        int select = -1;
        while(!isLogin) {
            String startMeu = "1. 登录	2.找回密码";
            showMessageLine(startMeu);
            select = scanner.nextInt();
            switch (select) {
                case 1:
                    this.login();
                    break;
                case 2:
                    this.findPassword();
                    break;
            }
        }
        while(isLogin) {
            this.menu();
            select = scanner.nextInt();
            switch (select) {
                case 3:
                    this.findAllDatas();
                    break;
                case 4:
                    this.findFuzzy();
                    break;
                case 5:
                    this.add();
                    break;
                case 6:
                    this.modify();
                    break;
                case 7:
                    this.exit();
                    break;
                default:
                    showMessageLine("未知的操作，请重新选择");
                    break;
            }
        }
    }
}
