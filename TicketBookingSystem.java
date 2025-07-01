import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Booking class
class Booking {
    private Show show;
    private List<Integer> seatNumbers;

    public Booking(Show show, List<Integer> seatNumbers) {
        this.show = show;
        this.seatNumbers = seatNumbers;
    }

    @Override
    public String toString() {
        return "Booking - " + show.getMovie().getTitle() + " at " + show.getShowTime() + ", Seats: " + seatNumbers;
    }
}

// Movie class
class Movie {
    private String title;
    private int duration;

    public Movie(String title, int duration) {
        this.title = title;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return title + " (" + duration + " mins)";
    }
}

// Show class
class Show {
    private Movie movie;
    private String showTime;
    private int totalSeats;
    private boolean[] seats;

    public Show(Movie movie, String showTime, int totalSeats) {
        this.movie = movie;
        this.showTime = showTime;
        this.totalSeats = totalSeats;
        this.seats = new boolean[totalSeats];
    }

    public Movie getMovie() {
        return movie;
    }

    public String getShowTime() {
        return showTime;
    }

    public boolean isSeatAvailable(int seatNumber) {
        return seatNumber >= 1 && seatNumber <= totalSeats && !seats[seatNumber - 1];
    }

    public boolean bookSeat(int seatNumber) {
        if (isSeatAvailable(seatNumber)) {
            seats[seatNumber - 1] = true;
            return true;
        }
        return false;
    }

    public void displayAvailableSeats() {
        System.out.print("Available Seats: ");
        for (int i = 0; i < totalSeats; i++) {
            if (!seats[i]) {
                System.out.print((i + 1) + " ");
            }
        }
        System.out.println();
    }

    @Override
    public String toString() {
        return movie + " - Showtime: " + showTime;
    }
}

// Main Ticket Booking System class
public class TicketBookingSystem {
    private List<Movie> movies = new ArrayList<>();
    private List<Show> shows = new ArrayList<>();
    private List<Booking> bookings = new ArrayList<>();

    public void addSampleData() {
        // Adding your specified movies
        Movie movie1 = new Movie("3 Idiots", 170);
        Movie movie2 = new Movie("Munna Bhai M.B.B.S.", 165);
        Movie movie3 = new Movie("Dhoom", 162);
        Movie movie4 = new Movie("Mr. India", 179);
        addMovie(movie1);
        addMovie(movie2);
        addMovie(movie3);
        addMovie(movie4);

        // Adding shows for the movies
        addShow(new Show(movie1, "10:00 AM", 10));
        addShow(new Show(movie2, "1:00 PM", 10));
        addShow(new Show(movie3, "4:00 PM", 10));
        addShow(new Show(movie4, "7:00 PM", 10));
    }

    public void addMovie(Movie movie) {
        movies.add(movie);
    }

    public void addShow(Show show) {
        shows.add(show);
    }

    public void displayMovies() {
        System.out.println("\nAvailable Movies:");
        for (int i = 0; i < movies.size(); i++) {
            System.out.println((i + 1) + ". " + movies.get(i));
        }
    }

    public void displayShows() {
        System.out.println("\nAvailable Shows:");
        for (int i = 0; i < shows.size(); i++) {
            System.out.println((i + 1) + ". " + shows.get(i));
        }
    }

    public void bookTickets(int showIndex, List<Integer> seatNumbers) {
        Show selectedShow = shows.get(showIndex - 1);
        List<Integer> bookedSeats = new ArrayList<>();

        for (int seatNumber : seatNumbers) {
            if (selectedShow.bookSeat(seatNumber)) {
                bookedSeats.add(seatNumber);
            } else {
                System.out.println("Seat " + seatNumber + " is already booked or invalid.");
            }
        }

        if (!bookedSeats.isEmpty()) {
            Booking booking = new Booking(selectedShow, bookedSeats);
            bookings.add(booking);
            System.out.println("Booking successful: " + booking);
        } else {
            System.out.println("No seats were booked. Please try again.");
        }
    }

    public void startBookingSystem() {
        Scanner scanner = new Scanner(System.in);
        addSampleData();

        while (true) {
            System.out.println("\nMovie Ticket Booking System:");
            System.out.println("1. Display Movies");
            System.out.println("2. Display Shows");
            System.out.println("3. Book Tickets");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    displayMovies();
                    break;
                case 2:
                    displayShows();
                    System.out.print("Select a show (number): ");
                    int showChoice = scanner.nextInt();
                    Show selectedShow = shows.get(showChoice - 1);
                    selectedShow.displayAvailableSeats();
                    break;
                case 3:
                    System.out.print("Select Show (number): ");
                    int showIndex = scanner.nextInt();
                    System.out.print("Enter Seat Numbers to Book (comma-separated): ");
                    scanner.nextLine();  // Consume the newline
                    String[] seatInputs = scanner.nextLine().split(",");
                    List<Integer> seatNumbers = new ArrayList<>();
                    for (String seat : seatInputs) {
                        seatNumbers.add(Integer.parseInt(seat.trim()));
                    }
                    bookTickets(showIndex, seatNumbers);
                    break;
                case 4:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    
    public static void main(String[] args) {

        TicketBookingSystem Obj = new TicketBookingSystem();
        Obj.startBookingSystem();
    }
}

