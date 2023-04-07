import java.util.ArrayList;

// define a class for musicTrack
class MusicTrack{
    //each track has a name and a duration
    String name;
    int duration;

    public MusicTrack(String name, int duration) {
        this.name = name;
        this.duration = duration;
    }
}

// implement the interface iBackup so that backup dancers and backup singers can override the backup method
interface IBackup{
    void Backup();
}

//create an abstract class for artists
abstract class artist{
    String name;
    public artist(String name) {
        this.name = name;
    }
    // define the methods sing() and dance in order to implement them
    // inside their relevant classes
    void sing(){
    }
    void dance(){
    }
    void interact(){
    }
}
//create the subclass singer which extends from the abstract class artist
class singer extends artist {
    public singer(String name) {
        super(name);
    }

    void sing(){
        System.out.println("This is the method sing() in the class singer");
    }

}
// creat the subclass backu singer
class backupSinger extends singer implements IBackup{
    public backupSinger(String name) {
        super(name);
    }

    @Override
    public void Backup() {
        System.out.println("This is the method Backup() in the class Backup singer and this is implemented from the Interface IBackup");
    }
}

//create the subclass backup dancer which extends from the abstract class artist
class backupDancer extends artist {
    public backupDancer(String name) {
        super(name);
    }
    void Backup(){
        System.out.println("This is the method Backup() in the class backupDancer and this is implemented from the Interface IBackup");
    }
    void Dance(){
        System.out.println("This is the method dance() in the class backup dancer");
    }
}

// create an abstract class for performance and some methods in this will be overwritten later
abstract class Performance {

    String main_artist;
    public String performance_name;
    private int year;
    private String venue;
    private ArrayList<MusicTrack> trackList ;
    ArrayList<backupSinger> backup_singers ;
    ArrayList<backupDancer> backup_dancers;
    // construutor for the performance class
    public Performance(String name , String artist,int year,String venue){
        this.main_artist = artist;
        this.performance_name = name;
        this.year = year;
        this.venue = venue;
        this.backup_singers = new ArrayList<backupSinger>();
        this.backup_dancers = new ArrayList<backupDancer>();

        System.out.println("Welcome to the "+ name + " by " + artist + "!!");
    }
    // year and venue are private attributes
    // But we need tobe able to access their values so for that we need to create getters
    public int getYear() {
        return year;
    }

    public void add_dancers(backupDancer dancer){
        this.backup_dancers.add(dancer);
    }
    public void add_backup_singers(backupSinger singer){
        this.backup_singers.add(singer);
    }

    public String getVenue() {
        return venue;
    }
    // because performance name can be viewed and changed by anyone we need to
    // create both getters and setters for them
    public String getPerformance_name() {
        return performance_name;
    }
    // anyone can set the performance name using this method
    public void setPerformance_name(String performance_name) {
        this.performance_name = performance_name;
    }

    // anyone can set the track list using this function
    public void setTrackList(ArrayList<MusicTrack> trackList) {
        this.trackList = trackList;
    }

    // this is an abstract method which will be overwritten inside live performance
    // and recorded performances classes

    public void Record(){
        // this method will be overwritten
    }

}

// create the live performance class
class Live_performance extends Performance {
    ArrayList<backupSinger> backup_singers;
    public Live_performance(String name, String artist, int year, String venue) {
        super(name, artist, year, venue);

    }
    public void Record(){
        System.out.println("This is the Record() method in live performance class");
    }
    // In live performance artists can interact
    public void Interact(){
        System.out.println("This is the Interact() method in Live_performance class when this is called artists interact with the crowd");
    }
}

// create the recorded performance class
class Recorded_performance extends Performance {
    public Recorded_performance(String name, String artist, int year, String venue) {
        super(name, artist, year, venue);
    }
    public void Record(){
        System.out.println("This is the Record() method in Recorded performance class");
    }
    // Recorded performances have a functions of audio Processing
    public void AudioProcess(){
        System.out.println("Ths is the AudioProcess() method is Recorder performance class");
    }
}

// create the music show
public class Music_Show {
    public static void main(String[] args) {
        // create music track objects for given music tracks
        MusicTrack one = new MusicTrack("Lavender Haze",3);
        MusicTrack two = new MusicTrack("All too well",3);
        MusicTrack three = new MusicTrack("The Man" ,3);
        MusicTrack four = new MusicTrack("Love story",3);
        ArrayList<MusicTrack> track_list_1 = new ArrayList<>();
        track_list_1.add(one);
        track_list_1.add(two);
        track_list_1.add(three);
        track_list_1.add(four);

        // initialize the main artist
        singer main_artist = new singer("Taylor swift");

        // initialize backup singers
        backupSinger backup_1 = new backupSinger("Jeslyn");
        backupSinger backup_2 = new backupSinger("Malanie");

        // initialize backup dancers
        backupDancer backup_D_1 = new backupDancer("Stephanie");
        backupDancer backup_D_2 = new backupDancer("jake");

        //Initialize a live performance for the give scenario
        Live_performance concert_1 = new Live_performance("Eras Tour","Taylor Swift",2023,"Glendale");

        // set the track list for the concert
        concert_1.setTrackList(track_list_1);

        // add backup dancers
        concert_1.add_dancers(backup_D_1);
        concert_1.add_dancers(backup_D_2);

        // add backup singers
        concert_1.add_backup_singers(backup_1);
        concert_1.add_backup_singers(backup_2);
        
        //concert starts main artist sings and the other artists back him up
        main_artist.sing();
        for(backupDancer dancer :concert_1.backup_dancers){
            dancer.Backup();
        }
        concert_1.Record();

    }
}
