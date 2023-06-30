package xyz.vshmaliukh.tucantournament.controllers.rest;

import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;
import xyz.vshmaliukh.tucantournament.model.dto.MostValuablePlayer;
import xyz.vshmaliukh.tucantournament.services.StatsService;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;

class StatsControllerTest {

    @Test
    void testDoPostFindMVPWithNoFiles() {
        StatsService statsService = mock(StatsService.class);

        StatsController statsController = new StatsController(statsService);

        MostValuablePlayer mvp = statsController.doPostFindMVP(new MultipartFile[0]);

        verifyNoInteractions(statsService);
        assertNull(mvp);
    }

}