package BankingSystem

import BankAccount.Account
import CaseClasses.{balanceResponse, createAccount, deposit, depositComplete, depositToAccount, getBalance, getBalanceOfAccount, transferAddition, transferCompleteConfirmation, transferDeduct, transferDeductConfirmation, transferDeductFailed, transferMoney, withdraw, withdrawFromAccount, withdrawalResponse}
import akka.actor.{Actor, ActorRef, Props}

import scala.collection.mutable

class BankingSystem extends Actor {

  val accountMapping = new mutable.HashMap[Int, ActorRef]() //Move to DB

  def receive:Receive = {

    case createAccount(accountID) =>
      val actorRef = context.actorOf(Props[Account],"Account"+accountID)
      accountMapping.put(accountID,actorRef)
      println("New account created. Account number = "+accountID)

    case getBalanceOfAccount(accountID) =>
      accountMapping.get(accountID) match {
        case Some(a) =>
          a!getBalance(accountID)
        case None => println("Account with ID:"+accountID +" does not exist")
      }
    case balanceResponse(accountID,balance) =>
      println("Balance of account "+accountID+" is "+balance)

    case depositToAccount(accountID,money) =>
      accountMapping.get(accountID) match {
        case Some(a) =>
          a!deposit(accountID,money)
        case None => println("Account with ID:"+accountID +" does not exist")
      }
    case depositComplete(accountID,balance) =>
      println("Deposit Successful")
      println( "Account "+accountID+" balance is now "+balance)

    case withdrawFromAccount(accountID,money) =>
      accountMapping.get(accountID) match {
        case Some(a) =>
          a!withdraw(accountID,money)
        case None => println("Account with ID:"+accountID +" does not exist")
      }
    case withdrawalResponse(accountID:Int,status:Boolean,balance:Int) =>
      if(status) {
        println("Withdrawal Successful.")
        println( "Account "+accountID+" balance is now "+balance)
      }
      else {
        println("Withdrawal unsuccessful. Account balance insufficient")
        println( "Account "+accountID+" balance is "+balance)
      }

    case transferMoney(fromAccount,toAccount,money) =>
      println("In transfermoney")
      accountMapping.get(fromAccount) match {
        case Some(a1) =>
          accountMapping.get(toAccount) match {
            case Some(a2) =>
              a1!transferDeduct(fromAccount,toAccount,money)
            case None => println("Account with ID:"+toAccount +" does not exist")
          }
        case None => println("Account with ID:"+fromAccount +" does not exist")
      }
    case transferDeductConfirmation(fromAccount,toAccount,money) =>
      accountMapping.get(toAccount) match {
        case Some(a2) =>
          a2!transferAddition(fromAccount,toAccount, money)
        case None => println("Account with ID:" + toAccount + " does not exist")
      }
    case transferDeductFailed(fromAccount,toAccount,money) =>
      println("Transfer of "+money+" amount from Account "+fromAccount+" to Account "+toAccount+" unsuccessful. Account balance insufficient")
    case transferCompleteConfirmation(fromAccount,toAccount,money) =>
      println(money+" successfully transferred from Account "+fromAccount+" to Account "+toAccount)
      accountMapping.get(fromAccount) match {
        case Some(a1) =>
          accountMapping.get(toAccount) match {
            case Some(a2) =>
              a1!getBalance(fromAccount)
              a2!getBalance(toAccount)
            case None => println("Account with ID:"+toAccount +" does not exist")
          }
        case None => println("Account with ID:"+fromAccount +" does not exist")
      }
  }
}
