/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package lifesimulator;

/**
 *
 * @author Student
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class LifeSimulator {

    static Scanner input = new Scanner(System.in);
    static Random random = new Random();
    static Game game = new Game();

    static final String[] CAREERS = {
        "Unemployed",
        "Software Developer",
        "Doctor",
        "Lawyer",
        "Teacher",
        "Police Officer",
        "Chef",
        "Nurse",
        "Fashion Designer",
        "Musician",
        "Actor",
        "Business Owner",
        "Influencer",
        "Journalist",
        "Engineer",
        "Professional Athlete"
    };

    static final String[] FIRST_NAMES = {
        "Lerato", "Thabo", "Amara", "Sipho", "Zanele",
        "Neo", "Mia", "Luca", "Ayesha", "Kabelo",
        "Naledi", "Daniel", "Kiara", "Tumelo", "Liam",
        "Nandi", "Jayden", "Lesedi", "Musa", "Kea"
    };

    static final String[] LAST_NAMES = {
        "Mokoena", "Dlamini", "Smith", "Nkosi", "Naidoo",
        "Molefe", "Khumalo", "Jacobs", "Pillay", "Mabena",
        "Ndlovu", "Williams", "Botha", "Mahlangu", "Mthembu"
    };

    static final String SAVE_FILE = "lifesimulator_save.dat";

    public static void main(String[] args) {
        System.out.println("==================================");
        System.out.println("       LIFE SIMULATOR");
        System.out.println("  Your life. Your choices. Drama.");
        System.out.println("==================================");

        System.out.println("1. Start a new life");
        System.out.println("2. Load saved game");

        int choice = readInt("Choose an option: ");

        if (choice == 2) {
            loadGame();
        } else {
            createCharacter();
        }

        while (game.playing) {
            showMainMenu();
        }

        System.out.println("\nThanks for playing Life Simulator!");
    }

    // Creates the player's character.
    static void createCharacter() {
        System.out.print("\nWhat is your character's name? ");
        String name = input.nextLine().trim();

        if (name.isEmpty()) {
            name = "Alex";
        }

        game = new Game();
        game.playerName = name;
        game.playing = true;

        System.out.println("\nWelcome, " + game.playerName + "!");
        System.out.println("You start with R5,000.");
        System.out.println("Your mission is to survive life, make money");
        System.out.println("and try not to become the town's biggest scandal.");

        chooseCareer();
    }

    static void showMainMenu() {
        System.out.println("\n==================================");
        System.out.println("Day " + game.day + " - " + game.playerName);
        System.out.println("Money: " + money(game.money));
        System.out.println("==================================");

        System.out.println("1. Go to work");
        System.out.println("2. Relax");
        System.out.println("3. Socialise");
        System.out.println("4. Relationships and drama");
        System.out.println("5. Shop for everyday items");
        System.out.println("6. Sleep");
        System.out.println("7. View character status");
        System.out.println("8. End the day");
        System.out.println("9. Save game");
        System.out.println("10. Load game");
        System.out.println("11. Property and housing");
        System.out.println("12. Buy a car");
        System.out.println("13. Luxury shopping");
        System.out.println("14. Host a party");
        System.out.println("15. Reputation and social status");
        System.out.println("16. Change career");
        System.out.println("17. Quit game");

        int choice = readInt("What would you like to do? ");

        switch (choice) {
            case 1:
                goToWork();
                break;
            case 2:
                relax();
                break;
            case 3:
                socialise();
                break;
            case 4:
                relationshipMenu();
                break;
            case 5:
                everydayShopping();
                break;
            case 6:
                sleep();
                break;
            case 7:
                showStatus();
                break;
            case 8:
                endDay();
                break;
            case 9:
                saveGame();
                break;
            case 10:
                loadGame();
                break;
            case 11:
                propertyMenu();
                break;
            case 12:
                carMenu();
                break;
            case 13:
                luxuryShopping();
                break;
            case 14:
                hostParty();
                break;
            case 15:
                showReputation();
                break;
            case 16:
                chooseCareer();
                break;
            case 17:
                game.playing = false;
                break;
            default:
                System.out.println("That is not a valid option.");
        }
    }

    // Lets the player choose from the available careers.
    static void chooseCareer() {
        System.out.println("\nAvailable careers:");

        for (int i = 1; i < CAREERS.length; i++) {
            System.out.println(i + ". " + CAREERS[i]);
        }

        System.out.println("0. Stay unemployed");

        int choice = readInt("Choose your career: ");

        if (choice < 0 || choice >= CAREERS.length) {
            System.out.println("Invalid career choice.");
            return;
        }

        game.career = CAREERS[choice];
        game.level = 1;
        game.experience = 0;

        if (choice == 0) {
            game.dailySalary = 0;
            System.out.println("You are unemployed. Time to find a way to make money!");
        } else {
            game.dailySalary = 250 + (choice * 35);
            System.out.println("You are now a " + game.career + "!");
            System.out.println("Your starting daily salary is "
                    + money(game.dailySalary) + ".");
        }
    }

    static void goToWork() {
        if (game.career.equals("Unemployed")) {
            System.out.println("You do not have a job yet.");
            System.out.println("Choose a career or try a side hustle.");

            System.out.println("1. Choose a career");
            System.out.println("2. Do a side hustle");

            int choice = readInt("Choose: ");

            if (choice == 1) {
                chooseCareer();
            } else if (choice == 2) {
                sideHustle();
            }

            return;
        }

        if (game.energy < 15) {
            System.out.println("You are too tired to work properly.");
            System.out.println("Get some rest first.");
            return;
        }

        int bonus = random.nextInt(151);
        int earnings = game.dailySalary + bonus;

        game.money += earnings;
        game.energy -= 20;
        game.happiness -= 3;
        game.experience += 20;

        System.out.println("\nYou finished a day at work.");
        System.out.println("You earned " + money(earnings) + ".");

        if (game.experience >= game.level * 100) {
            game.experience = game.experience - (game.level * 100);
            game.level++;

            game.dailySalary += 150;
            game.reputation += 3;

            System.out.println("PROMOTION!");
            System.out.println("You are now level " + game.level + ".");
            System.out.println("Your daily salary has increased.");
        }

        randomEvent();
        updateSocialStatus();
    }

    static void sideHustle() {
        if (game.energy < 10) {
            System.out.println("You are too tired to do a side hustle.");
            return;
        }

        int earnings = 100 + random.nextInt(401);

        game.money += earnings;
        game.energy -= 10;
        game.happiness -= 2;

        System.out.println("You completed a side hustle and earned "
                + money(earnings) + ".");

        randomEvent();
    }

    static void relax() {
        System.out.println("\nHow would you like to relax?");
        System.out.println("1. Watch a movie - R50");
        System.out.println("2. Go for a walk - free");
        System.out.println("3. Visit a cafe - R100");

        int choice = readInt("Choose: ");

        if (choice == 1) {
            if (spendMoney(50)) {
                game.happiness += 15;
                game.energy += 5;
                System.out.println("You watched a movie and feel better.");
            }
        } else if (choice == 2) {
            game.happiness += 10;
            game.health += 5;
            game.energy += 5;
            System.out.println("The walk cleared your mind.");
        } else if (choice == 3) {
            if (spendMoney(100)) {
                game.happiness += 15;
                System.out.println("You enjoyed a nice cafe visit.");
            }
        } else {
            System.out.println("Invalid choice.");
        }

        fixStats();
    }

    static void socialise() {
        System.out.println("\nSocial options:");
        System.out.println("1. Meet someone new");
        System.out.println("2. Spend time with a friend");
        System.out.println("3. Go on a date");
        System.out.println("4. Attend a social event");

        int choice = readInt("Choose: ");

        if (choice == 1) {
            meetSomeone();
        } else if (choice == 2) {
            spendTimeWithFriend();
        } else if (choice == 3) {
            goOnDate();
        } else if (choice == 4) {
            socialEvent();
        } else {
            System.out.println("Invalid choice.");
        }
    }

    static void meetSomeone() {
        String name = randomName();

        Person person = new Person();
        person.name = name;
        person.relationshipType = "Friend";
        person.friendship = 15 + random.nextInt(16);
        person.romance = 0;
        person.trust = 10 + random.nextInt(21);

        game.people.add(person);
        game.reputation += 1;
        game.happiness += 5;

        System.out.println("\nYou met " + name + ".");
        System.out.println("You exchanged numbers and became friends.");

        if (random.nextInt(100) < 20) {
            person.relationshipType = "Rival";
            person.friendship = 0;
            person.trust = 0;

            System.out.println(name + " already seems jealous of you.");
            System.out.println("Congratulations, you have a rival.");
        }

        updateSocialStatus();
        fixStats();
    }

    static void spendTimeWithFriend() {
        Person person = choosePerson();

        if (person == null) {
            return;
        }

        person.friendship += 10;
        person.trust += 5;

        game.happiness += 8;
        game.energy -= 5;

        System.out.println("You spent time with " + person.name + ".");
        System.out.println("Your friendship has improved.");
        fixStats();
    }

    static void goOnDate() {
        Person person = choosePerson();

        if (person == null) {
            return;
        }

        if (person.relationshipType.equals("Enemy")
                || person.relationshipType.equals("Rival")) {
            System.out.println(person.name + " is not interested in a date with you.");
            return;
        }

        if (!spendMoney(150)) {
            return;
        }

        person.romance += 15;
        person.trust += 5;
        person.friendship += 5;

        game.happiness += 12;
        game.energy -= 5;

        System.out.println("You went on a date with " + person.name + ".");

        if (person.romance >= 40
                && !person.relationshipType.equals("Partner")
                && random.nextInt(100) < 30) {
            System.out.println(person.name + " says they are catching feelings!");
        }

        randomEvent();
        fixStats();
    }

    static void socialEvent() {
        if (!spendMoney(100)) {
            return;
        }

        game.happiness += 10;
        game.reputation += 5;
        game.energy -= 10;

        System.out.println("You attended a social event and met interesting people.");

        if (random.nextInt(100) < 25) {
            System.out.println("Someone posted an embarrassing picture of you.");
            game.reputation -= 5;
            game.happiness -= 5;
        }

        updateSocialStatus();
        fixStats();
    }

    static void relationshipMenu() {
        System.out.println("\nRELATIONSHIPS AND DRAMA");
        System.out.println("1. View everyone");
        System.out.println("2. Ask someone to be your partner");
        System.out.println("3. Break up with your partner");
        System.out.println("4. Make someone your enemy");
        System.out.println("5. Start a rivalry");
        System.out.println("6. Make peace with someone");
        System.out.println("7. Gossip about someone");
        System.out.println("8. Check relationship drama");
        System.out.println("9. Talk to someone");

        int choice = readInt("Choose: ");

        switch (choice) {
            case 1:
                showRelationships();
                break;
            case 2:
                askToBePartner();
                break;
            case 3:
                breakUp();
                break;
            case 4:
                makeEnemy();
                break;
            case 5:
                startRivalry();
                break;
            case 6:
                makePeace();
                break;
            case 7:
                gossip();
                break;
            case 8:
                relationshipDrama();
                break;
            case 9:
                talkToSomeone();
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    static Person choosePerson() {
        if (game.people.isEmpty()) {
            System.out.println("You do not know anyone yet.");
            System.out.println("Go socialise and meet someone first.");
            return null;
        }

        showRelationships();

        int choice = readInt("Enter the person's number: ");

        if (choice < 1 || choice > game.people.size()) {
            System.out.println("Invalid person.");
            return null;
        }

        return game.people.get(choice - 1);
    }

    static void showRelationships() {
        System.out.println("\nPEOPLE IN YOUR LIFE");

        if (game.people.isEmpty()) {
            System.out.println("Your contact list is empty.");
            return;
        }

        for (int i = 0; i < game.people.size(); i++) {
            Person person = game.people.get(i);

            System.out.println((i + 1) + ". " + person.name
                    + " - " + person.relationshipType
                    + " | Friendship: " + person.friendship
                    + " | Romance: " + person.romance
                    + " | Trust: " + person.trust);

            if (person.isEx) {
                System.out.println("   Your ex");
            }
        }
    }

    static void askToBePartner() {
        Person person = choosePerson();

        if (person == null) {
            return;
        }

        if (person.isEx) {
            System.out.println("This person is your ex. Try rebuilding trust first.");
            return;
        }

        if (person.relationshipType.equals("Enemy")
                || person.relationshipType.equals("Rival")) {
            System.out.println("You cannot start a relationship with an enemy or rival.");
            return;
        }

        if (person.romance < 40 || person.friendship < 20) {
            System.out.println("You need more romance and friendship first.");
            return;
        }

        if (game.partnerName != null && !game.partnerName.isEmpty()) {
            System.out.println("You are already dating " + game.partnerName + ".");
            System.out.println("Break up first if you want to date someone else.");
            return;
        }

        person.relationshipType = "Partner";
        game.partnerName = person.name;

        game.happiness += 20;
        game.reputation += 2;

        System.out.println("\n" + person.name + " said YES!");
        System.out.println("You are now officially together.");

        updateSocialStatus();
        fixStats();
    }

    static void breakUp() {
        if (game.partnerName == null || game.partnerName.isEmpty()) {
            System.out.println("You are not currently in a relationship.");
            return;
        }

        Person partner = findPerson(game.partnerName);

        if (partner != null) {
            partner.relationshipType = "Friend";
            partner.isEx = true;
            partner.romance = 0;
            partner.friendship -= 10;
        }

        System.out.println("You broke up with " + game.partnerName + ".");
        System.out.println("The group chat is about to be interesting.");

        game.partnerName = "";
        game.happiness -= 15;
        game.reputation -= 2;

        updateSocialStatus();
        fixStats();
    }

    static void makeEnemy() {
        Person person = choosePerson();

        if (person == null) {
            return;
        }

        person.relationshipType = "Enemy";
        person.friendship = 0;
        person.trust = 0;
        person.romance = 0;

        game.reputation -= 2;

        System.out.println(person.name + " is now your enemy.");
        System.out.println("This friendship has officially left the group chat.");
        updateSocialStatus();
    }

    static void startRivalry() {
        Person person = choosePerson();

        if (person == null) {
            return;
        }

        person.relationshipType = "Rival";
        person.friendship = 0;
        person.trust = 0;

        System.out.println("You and " + person.name + " are now rivals.");
        System.out.println("Expect competition, jealousy and unnecessary drama.");
    }

    static void makePeace() {
        Person person = choosePerson();

        if (person == null) {
            return;
        }

        if (!person.relationshipType.equals("Enemy")
                && !person.relationshipType.equals("Rival")) {
            System.out.println("You are not enemies or rivals.");
            return;
        }

        person.relationshipType = "Friend";
        person.friendship = 10;
        person.trust = 5;

        game.happiness += 5;

        System.out.println("You made peace with " + person.name + ".");
        System.out.println("It is giving character development.");
        fixStats();
    }

    static void gossip() {
        Person person = choosePerson();

        if (person == null) {
            return;
        }

        System.out.println("You started gossiping about " + person.name + ".");

        if (random.nextInt(100) < 50) {
            game.reputation -= 10;
            game.happiness -= 5;
            person.trust -= 10;

            System.out.println("The gossip reached them.");
            System.out.println("They are upset, and your reputation has suffered.");
        } else {
            game.reputation += 2;
            System.out.println("The gossip stayed quiet. For now.");
        }

        updateSocialStatus();
        fixStats();
    }

    static void talkToSomeone() {
        Person person = choosePerson();

        if (person == null) {
            return;
        }

        System.out.println("You had a conversation with " + person.name + ".");

        if (person.relationshipType.equals("Enemy")) {
            System.out.println("The conversation became an argument.");
            person.trust -= 5;
            game.happiness -= 5;
        } else {
            person.friendship += 5;
            person.trust += 5;
            game.happiness += 5;
            System.out.println("You had a good conversation.");
        }

        fixStats();
    }

    static void relationshipDrama() {
        if (game.people.isEmpty()) {
            System.out.println("No relationships yet. Go meet people first.");
            return;
        }

        int event = random.nextInt(5);

        if (event == 0) {
            Person person = game.people.get(random.nextInt(game.people.size()));

            System.out.println(person.name + " has been talking about you.");
            game.reputation -= 5;
            game.happiness -= 5;
        } else if (event == 1) {
            System.out.println("Your old crush suddenly sends you a message.");
            System.out.println("'Hey stranger... been a while.'");

            Person ex = findPerson(game.exName);

            if (ex != null) {
                ex.romance += 10;
            } else {
                System.out.println("You do not have an ex in your contact list.");
            }
        } else if (event == 2 && !game.partnerName.isEmpty()) {
            System.out.println("Someone is flirting with your partner.");
            System.out.println("Your partner says it means nothing.");

            if (random.nextBoolean()) {
                game.happiness -= 10;
                game.reputation -= 2;
                System.out.println("You do not believe them. Drama!");
            } else {
                System.out.println("You decide to trust your partner.");
            }
        } else if (event == 3) {
            Person person = game.people.get(random.nextInt(game.people.size()));

            System.out.println(person.name + " has returned after disappearing.");
            System.out.println("They say they have changed.");

            person.trust += 5;
            person.friendship += 5;
        } else {
            Person person = game.people.get(random.nextInt(game.people.size()));

            System.out.println("A love triangle is forming around " + person.name + ".");
            System.out.println("Someone has caught feelings for the wrong person.");

            game.reputation += 2;
            game.happiness -= 3;
        }

        updateSocialStatus();
        fixStats();
    }

    static void everydayShopping() {
        System.out.println("\nEVERYDAY SHOPPING");
        System.out.println("1. Food - R80");
        System.out.println("2. New outfit - R250");
        System.out.println("3. Entertainment - R100");
        System.out.println("4. Cancel");

        int choice = readInt("Choose: ");

        if (choice == 1) {
            if (spendMoney(80)) {
                game.health += 10;
                game.happiness += 3;
                System.out.println("You bought food.");
            }
        } else if (choice == 2) {
            if (spendMoney(250)) {
                game.happiness += 10;
                game.reputation += 2;
                game.luxuryItems.add("Outfit");
                System.out.println("You bought a new outfit.");
            }
        } else if (choice == 3) {
            if (spendMoney(100)) {
                game.happiness += 8;
                System.out.println("You treated yourself to some entertainment.");
            }
        }

        updateSocialStatus();
        fixStats();
    }

    static void sleep() {
        game.energy += 40;
        game.health += 5;
        game.happiness += 2;

        System.out.println("You got some rest.");
        System.out.println("Your energy and health have improved.");

        fixStats();
    }

    static void showStatus() {
        System.out.println("\n========== CHARACTER STATUS ==========");
        System.out.println("Name: " + game.playerName);
        System.out.println("Day: " + game.day);
        System.out.println("Money: " + money(game.money));
        System.out.println("Career: " + game.career);
        System.out.println("Career level: " + game.level);
        System.out.println("Experience: " + game.experience);
        System.out.println("Daily salary: " + money(game.dailySalary));
        System.out.println("Energy: " + game.energy + "/100");
        System.out.println("Health: " + game.health + "/100");
        System.out.println("Happiness: " + game.happiness + "/100");
        System.out.println("Reputation: " + game.reputation);
        System.out.println("Social status: " + game.socialStatus);
        System.out.println("Home: " + game.home);
        System.out.println("Rent owed: " + money(game.rentOwed));
        System.out.println("Car: " + game.car);
        System.out.println("Partner: "
                + (game.partnerName.isEmpty() ? "Single" : game.partnerName));
        System.out.println("Friends and contacts: " + game.people.size());
        System.out.println("Properties owned: " + game.ownedProperties.size());
        System.out.println("Luxury items: " + game.luxuryItems.size());
        System.out.println("Parties hosted: " + game.partiesHosted);
        System.out.println("Days survived: " + game.daysSurvived + "/30");
        System.out.println("======================================");

        if (game.money < 0) {
            System.out.println("Warning: you are in debt!");
        }

        if (game.health <= 20) {
            System.out.println("Warning: your health is very low.");
        }

        if (game.happiness <= 20) {
            System.out.println("You need a break. Life is not looking good.");
        }
    }

    static void endDay() {
        System.out.println("\nThe day is coming to an end...");

        // Pay daily living costs.
        int livingCost = 100;
        game.money -= livingCost;

        System.out.println("Daily living expenses: " + money(livingCost));

        // Pay rent separately so paid rent does not become debt.
        if (!ownsCurrentHome() && getDailyRent() > 0) {
            int rent = getDailyRent();

            if (game.money >= rent) {
                game.money -= rent;
                game.missedRentDays = 0;

                System.out.println("You paid rent: " + money(rent));
            } else {
                game.rentOwed += rent;
                game.missedRentDays++;

                System.out.println("You could not pay your rent.");
                System.out.println("Rent debt: " + money(game.rentOwed));

                if (game.missedRentDays >= 3) {
                    System.out.println("You have missed rent three times.");
                    System.out.println("You have been evicted!");

                    game.home = "Shared Room";
                    game.rentOwed = 0;
                    game.missedRentDays = 0;
                }
            }
        }

        // Cars cost money to maintain.
        int carCost = getCarUpkeep();

        if (carCost > 0) {
            game.money -= carCost;
            System.out.println("Car upkeep: " + money(carCost));
        }

        game.day++;
        game.daysSurvived++;

        randomEvent();
        updateSocialStatus();
        fixStats();

        System.out.println("Day ended. Welcome to day " + game.day + ".");

        if (game.daysSurvived >= 30 && !game.challengeComplete) {
            game.challengeComplete = true;

            System.out.println("\n==================================");
            System.out.println("     30-DAY CHALLENGE COMPLETE!");
            System.out.println("==================================");
            System.out.println("You survived 30 days of your virtual life.");
            System.out.println("Final money: " + money(game.money));
            System.out.println("Final reputation: " + game.reputation);
            System.out.println("Social status: " + game.socialStatus);
            System.out.println("You have earned a little break!");
        }

        if (game.health <= 0) {
            System.out.println("\nYour health reached zero.");
            System.out.println("Your character must restart their life.");
            game.playing = false;
        }
    }

    static void propertyMenu() {
        System.out.println("\nPROPERTY AND HOUSING");
        System.out.println("Current home: " + game.home);
        System.out.println("Rent owed: " + money(game.rentOwed));
        System.out.println("1. Rent a shared room");
        System.out.println("2. Rent a basic apartment");
        System.out.println("3. Rent a modern apartment");
        System.out.println("4. Rent a luxury apartment");
        System.out.println("5. Rent a penthouse");
        System.out.println("6. Buy a property");
        System.out.println("7. Pay rent debt");
        System.out.println("8. Return to menu");

        int choice = readInt("Choose: ");

        switch (choice) {
            case 1:
                rentHome("Shared Room", 50, 500);
                break;
            case 2:
                rentHome("Basic Apartment", 150, 2500);
                break;
            case 3:
                rentHome("Modern Apartment", 300, 6000);
                break;
            case 4:
                rentHome("Luxury Apartment", 600, 12000);
                break;
            case 5:
                rentHome("Penthouse", 1200, 25000);
                break;
            case 6:
                buyProperty();
                break;
            case 7:
                payRentDebt();
                break;
            default:
                break;
        }
    }

    static void rentHome(String homeName, int dailyRent, int deposit) {
        if (ownsCurrentHome()) {
            System.out.println("You already own your current home.");
            System.out.println("You can choose another rental if you want.");
        }

        if (!spendMoney(deposit)) {
            return;
        }

        game.home = homeName;
        game.dailyRent = dailyRent;
        game.missedRentDays = 0;

        System.out.println("You moved into a " + homeName + ".");
        System.out.println("You paid a deposit of " + money(deposit) + ".");
        System.out.println("Daily rent is " + money(dailyRent) + ".");
    }

    static void buyProperty() {
        System.out.println("\nPROPERTIES FOR SALE");
        System.out.println("1. Basic Apartment - R50,000");
        System.out.println("2. Modern Apartment - R150,000");
        System.out.println("3. Luxury Apartment - R350,000");
        System.out.println("4. Penthouse - R900,000");
        System.out.println("5. Mansion - R2,000,000");

        int choice = readInt("Choose a property: ");

        String property;
        int price;

        switch (choice) {
            case 1:
                property = "Basic Apartment";
                price = 50000;
                break;
            case 2:
                property = "Modern Apartment";
                price = 150000;
                break;
            case 3:
                property = "Luxury Apartment";
                price = 350000;
                break;
            case 4:
                property = "Penthouse";
                price = 900000;
                break;
            case 5:
                property = "Mansion";
                price = 2000000;
                break;
            default:
                System.out.println("Invalid property choice.");
                return;
        }

        if (game.ownedProperties.contains(property)) {
            System.out.println("You already own this property.");
            return;
        }

        if (spendMoney(price)) {
            game.ownedProperties.add(property);
            game.home = property;
            game.dailyRent = 0;
            game.missedRentDays = 0;
            game.reputation += 10;

            System.out.println("Congratulations! You bought a " + property + ".");
            System.out.println("You no longer pay rent for this home.");

            updateSocialStatus();
        }
    }

    static void payRentDebt() {
        if (game.rentOwed <= 0) {
            System.out.println("You do not owe any rent.");
            return;
        }

        System.out.println("You owe " + money(game.rentOwed) + ".");
        System.out.println("1. Pay all rent debt");
        System.out.println("2. Pay part of the debt");
        System.out.println("3. Cancel");

        int choice = readInt("Choose: ");

        if (choice == 1) {
            if (game.money >= game.rentOwed) {
                game.money -= game.rentOwed;
                game.rentOwed = 0;
                game.missedRentDays = 0;
                System.out.println("All rent debt has been paid.");
            } else {
                System.out.println("You do not have enough money.");
            }
        } else if (choice == 2) {
            int amount = readInt("How much do you want to pay? ");

            if (amount > 0 && amount <= game.money) {
                amount = Math.min(amount, game.rentOwed);
                game.money -= amount;
                game.rentOwed -= amount;

                System.out.println("You paid " + money(amount) + ".");
            } else {
                System.out.println("Invalid amount or not enough money.");
            }
        }
    }

    static void carMenu() {
        System.out.println("\nCAR DEALERSHIP");
        System.out.println("Your current car: " + game.car);
        System.out.println("1. Used Hatchback - R15,000");
        System.out.println("2. Compact Sedan - R60,000");
        System.out.println("3. Luxury Sedan - R250,000");
        System.out.println("4. Sports Car - R700,000");
        System.out.println("5. Supercar - R2,500,000");
        System.out.println("6. Sell current car");
        System.out.println("7. Return to menu");

        int choice = readInt("Choose: ");

        if (choice >= 1 && choice <= 5) {
            String newCar;
            int price;

            switch (choice) {
                case 1:
                    newCar = "Used Hatchback";
                    price = 15000;
                    break;
                case 2:
                    newCar = "Compact Sedan";
                    price = 60000;
                    break;
                case 3:
                    newCar = "Luxury Sedan";
                    price = 250000;
                    break;
                case 4:
                    newCar = "Sports Car";
                    price = 700000;
                    break;
                default:
                    newCar = "Supercar";
                    price = 2500000;
                    break;
            }

            if (game.car.equals("No Car")) {
                if (spendMoney(price)) {
                    game.car = newCar;
                    game.reputation += 3;
                    System.out.println("You bought a " + newCar + "!");
                }
            } else {
                System.out.println("You already own a car.");
                System.out.println("Sell your current car before buying another.");
            }

            updateSocialStatus();
        } else if (choice == 6) {
            sellCar();
        }
    }

    static void sellCar() {
        if (game.car.equals("No Car")) {
            System.out.println("You do not have a car to sell.");
            return;
        }

        int salePrice = getCarPrice(game.car) / 2;

        game.money += salePrice;

        System.out.println("You sold your " + game.car + " for "
                + money(salePrice) + ".");

        game.car = "No Car";
        updateSocialStatus();
    }

    static void luxuryShopping() {
        System.out.println("\nLUXURY SHOPPING");
        System.out.println("1. Designer outfit - R2,000");
        System.out.println("2. Luxury handbag - R5,000");
        System.out.println("3. Designer watch - R8,000");
        System.out.println("4. Jewellery - R12,000");
        System.out.println("5. Designer shoes - R3,500");
        System.out.println("6. Luxury perfume - R1,500");
        System.out.println("7. View items you own");

        int choice = readInt("Choose: ");

        String item;
        int price;

        switch (choice) {
            case 1:
                item = "Designer Outfit";
                price = 2000;
                break;
            case 2:
                item = "Luxury Handbag";
                price = 5000;
                break;
            case 3:
                item = "Designer Watch";
                price = 8000;
                break;
            case 4:
                item = "Jewellery";
                price = 12000;
                break;
            case 5:
                item = "Designer Shoes";
                price = 3500;
                break;
            case 6:
                item = "Luxury Perfume";
                price = 1500;
                break;
            case 7:
                System.out.println("Luxury items: " + game.luxuryItems);
                return;
            default:
                System.out.println("Invalid choice.");
                return;
        }

        if (spendMoney(price)) {
            game.luxuryItems.add(item);
            game.happiness += 5;
            game.reputation += 2;

            System.out.println("You bought " + item + ".");
            updateSocialStatus();
            fixStats();
        }
    }

    static void hostParty() {
        System.out.println("\nHOST A PARTY");
        System.out.println("1. Small get-together - R500");
        System.out.println("2. House party - R2,000");
        System.out.println("3. Luxury party - R10,000");
        System.out.println("4. Celebrity-level party - R50,000");

        int choice = readInt("Choose: ");

        int cost;

        switch (choice) {
            case 1:
                cost = 500;
                break;
            case 2:
                cost = 2000;
                break;
            case 3:
                cost = 10000;
                break;
            case 4:
                cost = 50000;
                break;
            default:
                System.out.println("Invalid choice.");
                return;
        }

        if (!spendMoney(cost)) {
            return;
        }

        game.partiesHosted++;
        game.happiness += 10;
        game.reputation += 5;
        game.energy -= 10;

        System.out.println("Your party was a success!");

        if (random.nextInt(100) < 30) {
            System.out.println("Someone started drama at your party.");
            game.reputation -= 5;
            game.happiness -= 5;
        } else {
            System.out.println("People are talking about how good your party was.");
        }

        updateSocialStatus();
        fixStats();
    }

    static void showReputation() {
        updateSocialStatus();

        System.out.println("\nREPUTATION AND SOCIAL STATUS");
        System.out.println("Reputation: " + game.reputation);
        System.out.println("Social status score: " + game.socialStatus);

        if (game.socialStatus < 20) {
            System.out.println("Status: Unknown");
        } else if (game.socialStatus < 50) {
            System.out.println("Status: Known Around Town");
        } else if (game.socialStatus < 100) {
            System.out.println("Status: Popular");
        } else if (game.socialStatus < 200) {
            System.out.println("Status: VIP");
        } else {
            System.out.println("Status: Celebrity");
        }

        System.out.println("\nYour social status is affected by:");
        System.out.println("- Career level");
        System.out.println("- Reputation");
        System.out.println("- Properties you own");
        System.out.println("- Luxury items");
        System.out.println("- Parties you host");
        System.out.println("- The car you drive");
    }

    // Random events make every day a little different.
    static void randomEvent() {
        if (random.nextInt(100) >= 35) {
            return;
        }

        int event = random.nextInt(8);

        System.out.println("\n*** RANDOM LIFE EVENT ***");

        switch (event) {
            case 0:
                int bonus = 500 + random.nextInt(1501);
                game.money += bonus;
                System.out.println("You received unexpected money: "
                        + money(bonus) + ".");
                break;

            case 1:
                int bill = 200 + random.nextInt(801);
                game.money -= bill;
                System.out.println("An unexpected bill arrived: "
                        + money(bill) + ".");
                break;

            case 2:
                game.happiness += 10;
                System.out.println("You had a surprisingly good day.");
                break;

            case 3:
                game.health -= 10;
                System.out.println("You are feeling unwell and need to rest.");
                break;

            case 4:
                game.reputation += 5;
                System.out.println("Someone complimented your work.");
                break;

            case 5:
                if (!game.people.isEmpty()) {
                    Person person = game.people.get(
                            random.nextInt(game.people.size()));

                    person.trust -= 10;
                    game.reputation -= 3;

                    System.out.println(person.name
                            + " heard a rumour about you.");
                } else {
                    System.out.println("You had a quiet day with no drama.");
                }
                break;

            case 6:
                game.energy += 10;
                System.out.println("You found time for a refreshing break.");
                break;

            case 7:
                if (!game.partnerName.isEmpty()) {
                    System.out.println("Your partner surprises you with a gift.");
                    game.happiness += 10;
                } else {
                    System.out.println("You received a message from an old crush.");
                    game.happiness += 3;
                }
                break;
        }

        fixStats();
        updateSocialStatus();
    }

    static void updateSocialStatus() {
        // Recalculate the score instead of adding bonuses repeatedly.
        game.socialStatus =
                Math.max(0, game.reputation / 5)
                + game.level * 2
                + game.ownedProperties.size() * 10
                + game.luxuryItems.size() * 3
                + game.partiesHosted * 2
                + carIndex(game.car) * 3;
    }

    static void fixStats() {
        game.energy = Math.max(0, Math.min(100, game.energy));
        game.health = Math.max(0, Math.min(100, game.health));
        game.happiness = Math.max(0, Math.min(100, game.happiness));
        game.reputation = Math.max(0, game.reputation);

        for (Person person : game.people) {
            person.friendship = Math.max(0, Math.min(100, person.friendship));
            person.romance = Math.max(0, Math.min(100, person.romance));
            person.trust = Math.max(0, Math.min(100, person.trust));
        }
    }

    static boolean spendMoney(int amount) {
        if (amount < 0) {
            System.out.println("Invalid amount.");
            return false;
        }

        if (game.money < amount) {
            System.out.println("You do not have enough money.");
            System.out.println("You need " + money(amount) + ".");
            return false;
        }

        game.money -= amount;
        return true;
    }

    static int readInt(String message) {
        System.out.print(message);

        String line = input.nextLine().trim();

        try {
            return Integer.parseInt(line);
        } catch (NumberFormatException e) {
            System.out.println("Please enter a number.");
            return -1;
        }
    }

    static String money(int amount) {
        return "R" + String.format("%,d", amount);
    }

    static String randomName() {
        String name;

        do {
            String first = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
            String last = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
            name = first + " " + last;
        } while (findPerson(name) != null);

        return name;
    }

    static Person findPerson(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }

        for (Person person : game.people) {
            if (person.name.equalsIgnoreCase(name)) {
                return person;
            }
        }

        return null;
    }

    static boolean ownsCurrentHome() {
        return game.ownedProperties.contains(game.home);
    }

    static int getDailyRent() {
        if (ownsCurrentHome()) {
            return 0;
        }

        return game.dailyRent;
    }

    static int getCarUpkeep() {
        switch (game.car) {
            case "Used Hatchback":
                return 30;
            case "Compact Sedan":
                return 60;
            case "Luxury Sedan":
                return 150;
            case "Sports Car":
                return 300;
            case "Supercar":
                return 700;
            default:
                return 0;
        }
    }

    static int getCarPrice(String car) {
        switch (car) {
            case "Used Hatchback":
                return 15000;
            case "Compact Sedan":
                return 60000;
            case "Luxury Sedan":
                return 250000;
            case "Sports Car":
                return 700000;
            case "Supercar":
                return 2500000;
            default:
                return 0;
        }
    }

    static int carIndex(String car) {
        switch (car) {
            case "Used Hatchback":
                return 1;
            case "Compact Sedan":
                return 2;
            case "Luxury Sedan":
                return 3;
            case "Sports Car":
                return 4;
            case "Supercar":
                return 5;
            default:
                return 0;
        }
    }

    static void saveGame() {
        try (ObjectOutputStream output = new ObjectOutputStream(
                new FileOutputStream(SAVE_FILE))) {

            output.writeObject(game);
            System.out.println("Game saved successfully.");

        } catch (IOException e) {
            System.out.println("Could not save the game.");
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void loadGame() {
        try (ObjectInputStream file = new ObjectInputStream(
                new FileInputStream(SAVE_FILE))) {

            Object savedObject = file.readObject();

            if (savedObject instanceof Game) {
                game = (Game) savedObject;
                repairLoadedGame();

                System.out.println("Game loaded successfully.");
                System.out.println("Welcome back, " + game.playerName + "!");
            } else {
                System.out.println("The save file is not a valid game.");
            }

        } catch (FileNotFoundException e) {
            System.out.println("No saved game was found yet.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Could not load the saved game.");
            System.out.println("You may need to start a new game.");
        }
    }

    static void repairLoadedGame() {
        if (game.people == null) {
            game.people = new ArrayList<>();
        }

        if (game.ownedProperties == null) {
            game.ownedProperties = new ArrayList<>();
        }

        if (game.luxuryItems == null) {
            game.luxuryItems = new ArrayList<>();
        }

        if (game.playerName == null || game.playerName.isEmpty()) {
            game.playerName = "Alex";
        }

        if (game.career == null || game.career.isEmpty()) {
            game.career = "Unemployed";
        }

        if (game.home == null || game.home.isEmpty()) {
            game.home = "Shared Room";
        }

        if (game.car == null || game.car.isEmpty()) {
            game.car = "No Car";
        }

        if (game.partnerName == null) {
            game.partnerName = "";
        }

        if (game.exName == null) {
            game.exName = "";
        }

        game.playing = true;
        fixStats();
        updateSocialStatus();
    }

    // The player's saved game data.
    static class Game implements Serializable {

        private static final long serialVersionUID = 1L;

        String playerName = "Alex";
        String career = "Unemployed";
        String home = "Shared Room";
        String car = "No Car";
        String partnerName = "";
        String exName = "";

        int day = 1;
        int daysSurvived = 0;
        int money = 5000;
        int dailySalary = 0;
        int level = 1;
        int experience = 0;

        int energy = 100;
        int health = 100;
        int happiness = 75;
        int reputation = 10;
        int socialStatus = 0;

        int dailyRent = 50;
        int rentOwed = 0;
        int missedRentDays = 0;

        int partiesHosted = 0;

        boolean playing = true;
        boolean challengeComplete = false;

        ArrayList<Person> people = new ArrayList<>();
        ArrayList<String> ownedProperties = new ArrayList<>();
        ArrayList<String> luxuryItems = new ArrayList<>();
    }

    // Each person has their own relationship details.
    static class Person implements Serializable {

        private static final long serialVersionUID = 1L;

        String name = "";
        String relationshipType = "Friend";

        int friendship = 0;
        int romance = 0;
        int trust = 0;

        boolean isEx = false;
    }
}