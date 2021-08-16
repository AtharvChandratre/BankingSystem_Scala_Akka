import BankingSystem.BankingSystem
import CaseClasses.{createAccount, depositToAccount, getBalanceOfAccount, transferMoney}
import akka.actor.{ActorRef, ActorSystem, Props}

object BankingSystemPlayground extends App{

  val bankingSystem:ActorSystem = ActorSystem("BankingSystem")
  val bank:ActorRef = bankingSystem.actorOf(Props[BankingSystem], "BankingSystem")

  bank!createAccount(1)
  bank!createAccount(2)
  bank!getBalanceOfAccount(1)
  bank!depositToAccount(1,100)
  bank!transferMoney(1,2,40)

  bankingSystem.terminate()
}
