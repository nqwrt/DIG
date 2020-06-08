package withinterface;

import java.util.Random;
import java.util.Scanner;

import java.util.Random;
import java.util.Scanner;

/*전략(strategy) 디자인 패턴 적용*/
//애플리케이션에서 달라지는 부분을 찾아내고, 달라지지 않는 부분으로부터 분리시킨다.
/*디자인 원칙
- 애플리케이션에서 달라지는 부분을 찾아내고 달라지지 않는 부분으로 부터 분리시켜 캡슐화 한다. 
- 구현이 아닌 인터페이스에 맞쳐서 프로그래밍한다. 
- 상속보다 구성을 활용한다. ("A는 B이다" 보다 "A에는 B가 있다"가 나을 수 있다)
- 구성을 이용하여 시스템을 만들면 유연성을 크게 향상시킬 수 있다. 
- 알고리즘군을 별도의 클래스의 집합으로 캡슐화할 수 있도록 만들어 줄 뿐만 아니라, 실행시 행동을 바꿀있게 된다.
*/

interface RPCGetable{
   public static String[] RPC = {"가위","바위","보"};
   String generateRPC();   
}

class ComputerRPC  implements RPCGetable{

   @Override
   public String generateRPC() {
      Random random = new Random();
      int value = random.nextInt(3);
      
      return RPCGetable.RPC[value];      
   }   
}

class PersonRPC implements RPCGetable{
   
   public boolean isRPCString(String rpc) {
      boolean isRPC = false;
      
      for(String str : RPC) {
         if(rpc.trim().equals(str))
            return true;
      }      
      return isRPC;
   }
   
   @Override
   public String generateRPC() {
      System.out.println("가위 바위 보 입력하세요");
      Scanner scanner = null; 
      String personRPC;
      
      while(true) {
         try {         
            scanner = new Scanner(System.in);
            personRPC = scanner.next();            
            boolean isRPC = isRPCString(personRPC);
            
            if(isRPC == false) {
               System.out.println("잘못된 입력입니다. 다시 입력 해 주세요.");
               System.out.println("가위 바위 보 중에 하나를 넣어 주세요");
               continue;
            }else {
               break;
            }            
            
         } catch (Exception e) {
            e.printStackTrace();
            System.out.println("잘못된 입력입니다. 다시 입력 해 주세요.");
            continue;
         }   
      }
      return personRPC;
   }
}


class User {
   private String name;
   private String rpc;
   private String rpcNum;
   
   //전략패턴의 핵심
   /*상속이 아닌 구성을 활용*/
   RPCGetable rpcGetable;
   
   //주입
   //동적으로 가위바위보를 결정 알고리즘을 지정할수 있다
   public void setRpcGetable(RPCGetable rpcGetable) {
      this.rpcGetable = rpcGetable;
   }

   public void setRpc() {
      this.rpc = rpcGetable.generateRPC();
   }
   
   public int getRpcNum() {      
      for(int i=0;i<3;i++) {
         if(rpc.equals(RPCGetable.RPC[i])) {
            return i;
         }
      }      
      return 0;
   }
   
   public User(String name) {
      this.name = name;
   }
   
   public String getRpc() {
      return rpc;      
   }
   
   public void printCompare(User user) {
   
      System.out.println("-------- 가위 바위 보 !----------"); 
      System.out.printf("[ %s ] VS [ %s ] \n", this.name, user.name);
      
      int myNum = getRpcNum();
      int userNum = user.getRpcNum();
      
      
      if ((myNum == 0 && userNum == 0) || (myNum == 1 && userNum == 1) || (myNum == 2 && userNum == 2)){ 
            System.out.println(this.name + ":" + this.getRpc() + "  " + user.name + ":" + user.getRpc() +  " " +"무승부..."); 
      } else if ((myNum == 0 && userNum == 1) || (myNum == 1 && userNum == 2) || (myNum == 2 && userNum == 0)) { 
         System.out.println(this.name + ":" + this.getRpc() + "  " + user.name + ":" + user.getRpc() +" " +"당신의 패배ㅠㅠ"); 
      } 
      else if ((myNum == 0 && userNum == 2) || (myNum == 1 && userNum == 0) || (myNum == 2 && userNum == 1)) { 
         System.out.println(this.name + ":" + this.getRpc() + "  " + user.name + ":" + user.getRpc() + " " +"당신의 승리!");
      }
      
   }

}

class RPCGame{
   
   private static int count = 0; //횟수
   
   void execute() {
      User computer = new User("컴퓨터");
      computer.setRpcGetable(new ComputerRPC());
      
      User person = new User("사람");
      person.setRpcGetable(new PersonRPC());
      
      while(true) {
         
         computer.setRpc();
         person.setRpc();
         
         person.printCompare(computer);
         
         count++;
         System.out.println("현재는 " + count + "회 차입니다.");
         
      }
         
   }
         
}      
         
      
   


public class RPCTest {
   
   public static void main(String[] args) {
      RPCGame rpcGame = new RPCGame();
      rpcGame.execute();
   }
}
