package network.listeners;

import entity.Piece;
import network.entity.RestartRequest;

/**
 * @Description: TODO
 * @Author: Tomatos
 * @Date: 2025/6/5 09:22
 */
public interface NetworkMessageListener {
    void onPiecePlaced(Piece piece);

    void onOpponentQuit();

    void onOpponentWin();

    void onQueryResetGame(RestartRequest request);

    void onOpponentPlacePiece(Piece piece);

    void onResetGameConfirmed();
}
