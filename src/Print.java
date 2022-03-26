import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;

public class Print {
    public int singsCount = 1, albumsCount = 1, songsCount = 1;
    public int exit = 0;

    HashMap<String, ArrayList<String>> setSingers = new HashMap<>(); // 등록된 가수의 앨범 종류 key : 가수이름, value : 가수의 앨범종류
    HashMap<String, ArrayList<String>> setAlbums = new HashMap<>(); // 앨범에 수록된 key : 앨범이름, value : 노래제목

    ArrayList<String> newSinger = new ArrayList<>();
    ArrayList<String> newAlbum = new ArrayList<>();

    // 해당 가수에 해당하는 앨범 배열에 저장시키기
    public ArrayList<String> getAlbum(String singer){
        return setSingers.get(singer);
    }

    public void addAlbum(Singer singerSing, String album){
        String sing = singerSing.getSinger();
        if(setSingers.get(sing) == null) { // 아예 없는 가수면 새로 목록 만들기
            ArrayList<String> albumList = new ArrayList<>();
            setSingers.put(sing, albumList);
            getAlbum(sing).add(album); // 해당 가수의 Hashmap value에 값 추가
        }
        else if(!getAlbum(sing).contains(album))
            getAlbum(sing).add(album);
    }

    // 해당 가수 앨범에 해당하는 노래 저장시키기
    public ArrayList<String> getSong(String album){
        return setAlbums.get(album);
    }

    public void addSong(Album singerAlbum, String song){
        String album = singerAlbum.getAlbum();
        if(setAlbums.get(album) == null) { // 아예 없는 앨범이면 새로 목록 만들기
            ArrayList<String> songList = new ArrayList<>();
            setAlbums.put(album, songList);
            getSong(album).add(song); // 해당 앨범의 Hashmap value에 값 추가
        }
        else if(!getSong(album).contains(song))
            getSong(album).add(song); // 해당 앨범의 Hashmap value에 값 추가
    }

    public void selectAlbum(String keySinger){
        if (setSingers.containsKey(keySinger)) {
            System.out.println(keySinger + "에 수록된 앨범 목록 입니다.\r\n" + getAlbum(keySinger));
            System.out.println("앨범안에 노래를 보려면 선택해주세요.");
            Scanner albumSong = new Scanner(System.in);
            String albumTmp = albumSong.next();
            if(setAlbums.containsKey(albumTmp))
                selectSong(keySinger, albumTmp);
        }
        else
            System.out.println("해당하는 앨범의 노래는 존재하지 않습니다.");
    }

    public void selectSong(String keySinger, String keyAlbum){
        if (setAlbums.containsKey(keyAlbum)) {
            System.out.println(keySinger + " " + keyAlbum + "에 수록된 노래 목록 입니다.\r\n" + getSong(keyAlbum));
        }
    }

    //출력문 시작
    public void print() {
        while(exit == 0) {
            Singer[] Person = new Singer[singsCount];
            Album[] personAlbum = new Album[albumsCount];
            Song[] personSong = new Song[songsCount];

            System.out.println("----- 노래저장소에 오신것을 환영합니다. -----");
            System.out.println("1. 저장소   2. 종료");
            Scanner first = new Scanner(System.in);

            if(first.nextInt() == 1){
                System.out.println("1. 추가    2. 리스트   3. 종료");
                Scanner seconds = new Scanner(System.in);

                switch (seconds.nextInt()){
                    case 1:
                        System.out.println("\r\n목록에 추가할 가수: ");
                        Scanner scanSinger = new Scanner(System.in);
                        String sings = scanSinger.next();

                        System.out.println("목록에 추가할 앨범: ");
                        Scanner scanAlbum = new Scanner(System.in);
                        String albums = scanAlbum.next();

                        System.out.println("목록에 추가할 노래: ");
                        Scanner scanSong = new Scanner(System.in);
                        String songs = scanSong.next();

                        // 배열안에 집어넣고 싶으면 new 쓴다음 생성자 작성하기! 그래야 클래스에 들어가서 쓸수 있음!
                        Person[singsCount - 1] = new Singer(sings);
                        if(getAlbum(sings) == null) // 처음 등록되는 가수면 가수의 수를 늘려 반복횟수 증가시키기
                            newSinger.add(sings);
                        addAlbum(Person[singsCount - 1], albums);
                        singsCount++;

                        personAlbum[albumsCount - 1] = new Album(albums);
                        if(getSong(albums) == null) // 처음 등록되는 앨범이면 앨범의 수를 늘려 반복횟수 증가시키기
                            newAlbum.add(albums);
                        addSong(personAlbum[albumsCount - 1], songs);
                        albumsCount++;

                        personSong[songsCount-1] = new Song(songs);
                        songsCount++;

                        System.out.println("\r\n--- 입력하신 정보가 정상적으로 등록 되었습니다. ---");
                        System.out.println((singsCount-1)+"번째 추가된 가수: " + Person[singsCount-2].getSinger() +"\r\n"+ (albumsCount-1)+"번째 추가된 앨범: " + personAlbum[albumsCount-2].getAlbum() +"\r\n"+ (songsCount-1)+"번째 추가된 노래: " + personSong[songsCount-2].getSong());
                        System.out.println();
                        break;

                    case 2:
                        System.out.println("저장된 가수 목록 입니다.\r\n" + newSinger);
                        System.out.println("가수를 선택해주세요. 뒤로 돌아가시려면 E 또는 e키를 작성해주세요!");
                        Scanner choiceSinger = new Scanner(System.in);
                        String singerTmp = choiceSinger.next();
                        if(singerTmp.equals("e") || singerTmp.equals("E"))
                            break;
                        if(setSingers.containsKey(singerTmp))
                            selectAlbum(singerTmp);
                        else
                            System.out.println("해당 가수의 앨범은 존재하지 않습니다.");
                        System.out.println();
                        break;

                    case 3:
                        exit = 1;
                        break;

                    default:
                        break;
                }
            }
            else
                exit = 1;
        }
    }
}
