package Service;

import DAO.AccountDao;
import Model.Account;

public class AccountService {
    public AccountService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }
    public AccountService() {
       this.accountDao = new AccountDao(); 
    }
    private final AccountDao accountDao;

    // Register Account
    public Account registerAccount(String username, String password) {
        Account existingAccount = accountDao.getAccountByUsername(username);
        //blank and user carrying too many character
        if (existingAccount != null || username.equals("") || password.length() < 4) {
            // Account with the given username already exists
            return null;
        } else {
            Account newAccount = new Account(username, password);
            return accountDao.createAccount(newAccount);
        }
    }

    // Login Account
    public Account loginAccount(String username, String password) {
        Account account = accountDao.getAccountByUsername(username);
        if (account != null && account.getPassword().equals(password)) {
            // Successful login
            return account;
        } else {
            // Invalid username or password
            return null;
        }
    }

    // Add more methods as needed
     // Get Account by ID
     //public Account getAccountById(int accountId) {
        //return accountDao.getAccountById(accountId);
    //}
}
