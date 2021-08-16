package BankAccount

import CaseClasses.{balanceResponse, deposit, depositComplete, getBalance, transferAddition, transferCompleteConfirmation, transferDeduct, transferDeductConfirmation, transferDeductFailed, withdraw, withdrawalResponse}
import akka.actor.Actor

class Account extends Actor {
  var balance: Int = 0
  def receive:Receive = {

    case getBalance(accountID) =>
      sender!balanceResponse(accountID,balance)

    case deposit(accountID,money) =>
      balance+=money
      sender!depositComplete(accountID,balance)

    case withdraw(accountID,money) =>
      if(balance>=money) {
        balance-=money
        sender!withdrawalResponse(accountID,true,balance)
      }
      else{
        sender!withdrawalResponse(accountID,false,balance)
      }

    case transferDeduct(fromAccount, toAccount, money) =>
      if(balance>=money) {
        balance-=money
        sender!transferDeductConfirmation(fromAccount, toAccount, money)
      }
      else{
        sender!transferDeductFailed(fromAccount, toAccount, money)
      }

    case transferAddition(fromAccount, toAccount, money) =>
      balance+=money
      sender!transferCompleteConfirmation(fromAccount, toAccount, money)
  }
}
