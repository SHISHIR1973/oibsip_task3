import java.util.*;
import java.util.stream.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.*;

class Transaction_details{
	Transaction_details(String account_id){
		String filename = "transaction/"+account_id+".txt";
		try{
			FileReader filecontent = new FileReader(filename);
			int i;
			while((i=filecontent.read()) != -1){
				System.out.print((char)i);
			}
			filecontent.close();
		}
		catch(Exception e){}

	}
}

class Withdraw{
	String Balance_old_data;
	String Balance_new_data;

	String Transaction_data;

	float withdraw_amount;
	float current_balance;
	Withdraw(String account_id,int i) throws Exception{
		try (Stream<String> Bal = Files.lines(Paths.get("balance.txt"))) {
			Balance_old_data = Bal.skip(i).findFirst().get();
		}
		catch(Exception e){}
		for (String val: Balance_old_data.split("->")){
			current_balance = Float.parseFloat(val);
		}
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter withdraw amount: ");
		withdraw_amount = sc.nextFloat();
		if (current_balance >= withdraw_amount){
			current_balance = current_balance - withdraw_amount;
			System.out.println("Successfully withdrawed Rs. "+String.format("%.2f",withdraw_amount));
			System.out.println("Current Balance is Rs. "+String.format("%.2f",current_balance));
			
			String filename = "transaction/"+account_id+".txt";
			BufferedWriter update = new BufferedWriter(new FileWriter(filename,true));
			String space = new String(new char[26-(String.format("%.2f",withdraw_amount)).length()]).replace("\0"," ");
			Transaction_data = String.valueOf(LocalDate.now())+"      withdraw from "+String.valueOf(account_id)+"                    - "+String.format("%.2f",withdraw_amount)+space+String.format("%.2f",current_balance);
			update.newLine();
			update.append(Transaction_data);
			update.close();
			
			Balance_new_data = account_id+"->"+String.format("%.2f",current_balance);
			Path path = Paths.get("balance.txt");
            try (Stream<String> stream = Files.lines(path, StandardCharsets.UTF_8)) {
                List<String> new_data = stream.map(row -> row.equals(Balance_old_data) ? Balance_new_data : row).collect(Collectors.toList());
                Files.write(path, new_data, StandardCharsets.UTF_8);
            } catch (Exception e) {}

		}
		else{
			System.out.println("Unable to withdraw Rs. "+String.format("%.2f",withdraw_amount)+" . Low Balance!!");
		}
	}
}

class Deposit{
	String Balance_old_data;
	String Balance_new_data;

	String Transaction_data;

	float deposit_amount;
	float current_balance;
	Deposit(String account_id,int i) throws Exception{
		try (Stream<String> Bal = Files.lines(Paths.get("balance.txt"))) {
			Balance_old_data = Bal.skip(i).findFirst().get();
		}
		catch(Exception e){}
		for (String val: Balance_old_data.split("->")){
			current_balance = Float.parseFloat(val);
		}
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter Deposit amount: ");
		deposit_amount = sc.nextFloat();
		current_balance = current_balance + deposit_amount;
		System.out.println("Successfully deposited Rs. "+String.format("%.2f",deposit_amount));
		System.out.println("Current Balance is Rs. "+String.format("%.2f",current_balance));
		
		String filename = "transaction/"+account_id+".txt";
		BufferedWriter update = new BufferedWriter(new FileWriter(filename,true));
		String space = new String(new char[26-(String.format("%.2f",deposit_amount)).length()]).replace("\0"," ");
		Transaction_data = String.valueOf(LocalDate.now())+"      deposited to "+String.valueOf(account_id)+"                     + "+String.format("%.2f",deposit_amount)+space+String.format("%.2f",current_balance);
		update.newLine();
		update.append(Transaction_data);
		update.close();
		
		Balance_new_data = account_id+"->"+String.format("%.2f",current_balance);
		Path path = Paths.get("balance.txt");
        try (Stream<String> stream = Files.lines(path, StandardCharsets.UTF_8)) {
            List<String> new_data = stream.map(row -> row.equals(Balance_old_data) ? Balance_new_data : row).collect(Collectors.toList());
            Files.write(path, new_data, StandardCharsets.UTF_8);
        } catch (Exception e) {}
	}
}

class Transfer{
	String Balance_old_data;
	String Balance_new_data;

	String Transaction_data;

	float transfer_amount;
	float current_balance;
	Transfer(String account_id1,String account_id2,int i,int j) throws Exception{
		try (Stream<String> Bal = Files.lines(Paths.get("balance.txt"))) {
			Balance_old_data = Bal.skip(i).findFirst().get();
		}
		catch(Exception e){}
		for (String val: Balance_old_data.split("->")){
			current_balance = Float.parseFloat(val);
		}
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter transfer amount: ");
		transfer_amount = sc.nextFloat();
		if (current_balance >= transfer_amount){
			current_balance = current_balance - transfer_amount;
			System.out.println("Successfully transferred Rs. "+String.format("%.2f",transfer_amount));
			System.out.println("Current Balance is Rs. "+String.format("%.2f",current_balance));
			
			String filename = "transaction/"+account_id1+".txt";
			BufferedWriter update = new BufferedWriter(new FileWriter(filename,true));
			String space = new String(new char[26-(String.format("%.2f",transfer_amount)).length()]).replace("\0"," ");
			Transaction_data = String.valueOf(LocalDate.now())+"      tnf. "+String.valueOf(account_id1)+" to "+String.valueOf(account_id2)+"               - "+String.format("%.2f",transfer_amount)+space+String.format("%.2f",current_balance);
			update.newLine();
			update.append(Transaction_data);
			update.close();
			
			Balance_new_data = account_id1+"->"+String.format("%.2f",current_balance);
			Path path = Paths.get("balance.txt");
            try (Stream<String> stream = Files.lines(path, StandardCharsets.UTF_8)) {
                List<String> new_data = stream.map(row -> row.equals(Balance_old_data) ? Balance_new_data : row).collect(Collectors.toList());
                Files.write(path, new_data, StandardCharsets.UTF_8);
            } catch (Exception e) {}

			if (j >= 0){
				try (Stream<String> Bal = Files.lines(Paths.get("balance.txt"))) {
					Balance_old_data = Bal.skip(j).findFirst().get();
				}
				catch(Exception e){}
				for (String val: Balance_old_data.split("->")){
					current_balance = Float.parseFloat(val);
				}
				current_balance = current_balance + transfer_amount;
				
				String file2 = "transaction/"+account_id2+".txt";
				BufferedWriter update2 = new BufferedWriter(new FileWriter(file2,true));
				String space2 = new String(new char[26-(String.format("%.2f",transfer_amount)).length()]).replace("\0"," ");
				Transaction_data = String.valueOf(LocalDate.now())+"      tnf. from "+String.valueOf(account_id1)+"                        + "+String.format("%.2f",transfer_amount)+space2+String.format("%.2f",current_balance);
				update2.newLine();
				update2.append(Transaction_data);
				update2.close();
				
				Balance_new_data = account_id2+"->"+String.format("%.2f",current_balance);
				Path path2 = Paths.get("balance.txt");
				try (Stream<String> stream = Files.lines(path2, StandardCharsets.UTF_8)) {
					List<String> new_data2 = stream.map(row -> row.equals(Balance_old_data) ? Balance_new_data : row).collect(Collectors.toList());
					Files.write(path2, new_data2, StandardCharsets.UTF_8);
				} catch (Exception e) {}
			}

		}
		else{
			System.out.println("Unable to transfer Rs. "+String.format("%.2f",transfer_amount)+" . Low Balance!!");
		}
	}
}

public class ATM{
	static String username;
	static String username2;
	static String password;
	static String pwrd;
	
	static ArrayList<String> Account_holder = new ArrayList <String>();
	ATM() throws Exception{
		Scanner s1=new Scanner(System.in);
		System.out.print("USER ID: ");
		username = s1.next();
		System.out.print("PIN: ");
		password = s1.next();
		int index = Account_holder.indexOf(username);
		try (Stream<String> Pass = Files.lines(Paths.get("pass.txt"))) {
			pwrd = Pass.skip(index).findFirst().get();
		}
		catch(Exception e){}
		if (Account_holder.indexOf(username) > -1){
			if(password.equals(pwrd)){
				System.out.println("\nSuccessfully Logged in.");
				boolean flag=true;
				int ch;
				while(flag){
					System.out.println("\n\n1-> View Transactions History");
					System.out.println("2-> Withdraw Money");
					System.out.println("3-> Deposit Money");
					System.out.println("4-> Transfer Money");
					System.out.println("5-> Exit");
					System.out.print("\nChoose one Option:");
					ch=s1.nextInt();
					if (ch == 1){
						new Transaction_details(username);
					}
					else if (ch == 2){
						new Withdraw(username,index);
					}
					else if (ch == 3){
						new Deposit(username,index);
					}
					else if (ch == 4){
						System.out.print("Money Transfer Account USER ID: ");
						username2 = s1.next();
						int index2 = Account_holder.indexOf(username2);
						if (username == username2){
							System.out.println("Can not be transferred to same account!!");
						}
						else{
							new Transfer(username, username2, index,index2);
						}
					}
					else{
						System.out.println("Logged out Successfully!!");
						flag=false;
					}
				}
			}
			else{
				System.out.println("\nIncorrect Password!! Try again.\n");
				new ATM();
			}
		}
		else{
			System.out.println("\nInvalid Username!!\n");
			new ATM();
		}
	}
	public static void main(String[] args) throws Exception{
		try{
			BufferedReader reader = new BufferedReader(new FileReader("users.txt"));
			String row = reader.readLine();
			while (row != null){
				Account_holder.add(row);
				row = reader.readLine();
			}
			reader.close();
		}
		catch(Exception e){}
		System.out.println("\n################## BANK OF RUPEES ##################");
		new ATM();
	}
}