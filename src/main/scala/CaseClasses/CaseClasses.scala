package CaseClasses

  case class getBalance(accountID:Int)
  case class balanceResponse(accountID:Int, balance: Int)
  case class deposit(accountID:Int, money: Int)
  case class depositComplete(accountID:Int,balance:Int)
  case class withdraw(accountID:Int,money: Int)
  case class withdrawalResponse(accountID:Int,status: Boolean,balance:Int)
  case class createAccount(accountID:Int)
  case class getBalanceOfAccount(accountID: Int)
  case class depositToAccount(accountID:Int, money:Int)
  case class withdrawFromAccount(accountID: Int, money:Int)
  case class transferMoney(fromAccount:Int, toAccount:Int, money:Int)
  case class transferDeduct(fromAccount:Int, toAccount:Int, money:Int)
  case class transferDeductConfirmation(fromAccount:Int, toAccount:Int, money:Int)
  case class transferAddition(fromAccount:Int, toAccount:Int, money:Int)
  case class transferCompleteConfirmation(fromAccount:Int, toAccount:Int, money:Int)
  case class transferDeductFailed(fromAccount:Int, toAccount:Int, money:Int)
