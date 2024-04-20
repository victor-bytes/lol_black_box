import com.qq.lol.core.services.impl.RoomServiceImpl;
import com.qq.lol.core.services.RoomService;

/**
 * @Auther: null
 * @Date: 2023/12/5 - 12 - 05 - 12:54
 * @Description: TODO
 * @version: 1.0
 */
public class testRoomService {
    private static RoomService roomService = RoomServiceImpl.getRoomService();

    public static void main(String[] args) {
        roomService.getRoomInfo();
    }
}
