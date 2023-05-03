package com.example.diplombackend.service;

import com.example.diplombackend.model.description.PointDescription;
import com.example.diplombackend.model.figures.Point.PointType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PointServiceTest {
    @Autowired
    PointService pointService;

    static List<PointDescription> picture1 = List.of(
            PointDescription.builder().name("End").type(PointType.POINT).coordinate1(-1.0).coordinate2(2.0).build(),
            PointDescription.builder().name("Start").type(PointType.POINT).coordinate1(-5.0).coordinate2(2.0).build(),
            PointDescription.builder().name("Mid").type(PointType.MIDPOINT).coordinate1(-3.0).coordinate2(2.0).build()
    );

    @Test
    @Description("Draw random point")
    void testFindCommonPointDescription() {
        List<PointDescription> picture2 = List.of(
                PointDescription.builder().name("A").type(PointType.POINT).coordinate1(-10.0).coordinate2(10.0).build()
        );
        List<PointDescription> result = List.of(
                PointDescription.builder().name(null).type(PointType.POINT).coordinate1(null).coordinate2(null).build()
        );
        Assertions.assertEquals(result, pointService.findCommonPointDescription(picture2, picture1));
        Assertions.assertEquals(pointService.findCommonPointDescription(picture1, picture2),
                pointService.findCommonPointDescription(picture2, picture1));
    }
    @Test
    @Description("Draw random point named Start")
    void testFindCommonPointDescription2() {
        List<PointDescription> picture2 = List.of(
                PointDescription.builder().name("Start").type(PointType.POINT).coordinate1(-10.0).coordinate2(10.0).build()
        );
        List<PointDescription> result = List.of(
                PointDescription.builder().name("Start").type(PointType.POINT).coordinate1(null).coordinate2(null).build()
        );
        Assertions.assertEquals(result, pointService.findCommonPointDescription(picture2, picture1));
        Assertions.assertEquals(pointService.findCommonPointDescription(picture1, picture2),
                pointService.findCommonPointDescription(picture2, picture1));
    }
    @Test
    @Description("Draw random midpoint of points")
    void testFindCommonPointDescription3() {
        List<PointDescription> picture2 = List.of(
                PointDescription.builder().name("C").type(PointType.POINT).coordinate1(-10.0).coordinate2(10.0).build(),
                PointDescription.builder().name("D").type(PointType.POINT).coordinate1(-12.0).coordinate2(10.0).build(),
                PointDescription.builder().name("E").type(PointType.MIDPOINT).coordinate1(-11.0).coordinate2(10.0).build()
        );
        List<PointDescription> result = List.of(
                PointDescription.builder().name(null).type(PointType.POINT).coordinate1(null).coordinate2(null).build(),
                PointDescription.builder().name(null).type(PointType.POINT).coordinate1(null).coordinate2(null).build(),
                PointDescription.builder().name(null).type(PointType.MIDPOINT).coordinate1(null).coordinate2(null).build()
        );
        Assertions.assertEquals(result, pointService.findCommonPointDescription(picture2, picture1));
        Assertions.assertEquals(pointService.findCommonPointDescription(picture1, picture2),
                pointService.findCommonPointDescription(picture2, picture1));
    }
    @Test
    @Description("Draw midpoint on fixed coordinates of random points")
    void testFindCommonPointDescription4() {
        List<PointDescription> picture2 = List.of(
                PointDescription.builder().name("C").type(PointType.POINT).coordinate1(-10.0).coordinate2(10.0).build(),
                PointDescription.builder().name("D").type(PointType.POINT).coordinate1(-12.0).coordinate2(10.0).build(),
                PointDescription.builder().name("E").type(PointType.MIDPOINT).coordinate1(-3.0).coordinate2(2.0).build()
        );
        List<PointDescription> result = List.of(
                PointDescription.builder().name(null).type(PointType.POINT).coordinate1(null).coordinate2(null).build(),
                PointDescription.builder().name(null).type(PointType.POINT).coordinate1(null).coordinate2(null).build(),
                PointDescription.builder().name(null).type(PointType.MIDPOINT).coordinate1(-3.0).coordinate2(2.0).build()
        );
        Assertions.assertEquals(result, pointService.findCommonPointDescription(picture2, picture1));
        Assertions.assertEquals(pointService.findCommonPointDescription(picture1, picture2),
                pointService.findCommonPointDescription(picture2, picture1));
    }
    @Test
    @Description("Draw midpoint on fixed coordinates named Mid of random points")
    void testFindCommonPointDescription5() {
        List<PointDescription> picture2 = List.of(
                PointDescription.builder().name("C").type(PointType.POINT).coordinate1(-10.0).coordinate2(10.0).build(),
                PointDescription.builder().name("D").type(PointType.POINT).coordinate1(-12.0).coordinate2(10.0).build(),
                PointDescription.builder().name("Mid").type(PointType.MIDPOINT).coordinate1(-3.0).coordinate2(2.0).build()
        );
        List<PointDescription> result = List.of(
                PointDescription.builder().name(null).type(PointType.POINT).coordinate1(null).coordinate2(null).build(),
                PointDescription.builder().name(null).type(PointType.POINT).coordinate1(null).coordinate2(null).build(),
                PointDescription.builder().name("Mid").type(PointType.MIDPOINT).coordinate1(-3.0).coordinate2(2.0).build()
        );
        Assertions.assertEquals(result, pointService.findCommonPointDescription(picture2, picture1));
        Assertions.assertEquals(pointService.findCommonPointDescription(picture1, picture2),
                pointService.findCommonPointDescription(picture2, picture1));
    }
    @Test
    @Description("Draw midpoint on fixed coordinates named Mid of random points named Starr and End")
    void testFindCommonPointDescription6() {
        List<PointDescription> picture2 = List.of(
                PointDescription.builder().name("End").type(PointType.POINT).coordinate1(1.0).coordinate2(1.0).build(),
                PointDescription.builder().name("Start").type(PointType.POINT).coordinate1(-7.0).coordinate2(3.0).build(),
                PointDescription.builder().name("Mid").type(PointType.MIDPOINT).coordinate1(-3.0).coordinate2(2.0).build()
        );
        List<PointDescription> result = List.of(
                PointDescription.builder().name("End").type(PointType.POINT).coordinate1(null).coordinate2(null).build(),
                PointDescription.builder().name("Start").type(PointType.POINT).coordinate1(null).coordinate2(null).build(),
                PointDescription.builder().name("Mid").type(PointType.MIDPOINT).coordinate1(-3.0).coordinate2(2.0).build()
        );
        Assertions.assertEquals(result, pointService.findCommonPointDescription(picture2, picture1));
        Assertions.assertEquals(pointService.findCommonPointDescription(picture1, picture2),
                pointService.findCommonPointDescription(picture2, picture1));
    }
    @Test
    @Description("Draw midpoint on fixed coordinates named Mid of fixed points named Starr and End")
    void testFindCommonPointDescription7() {
        List<PointDescription> picture2 = List.of(
                PointDescription.builder().name("End").type(PointType.POINT).coordinate1(-1.0).coordinate2(2.0).build(),
                PointDescription.builder().name("Start").type(PointType.POINT).coordinate1(-5.0).coordinate2(2.0).build(),
                PointDescription.builder().name("Mid").type(PointType.MIDPOINT).coordinate1(-3.0).coordinate2(2.0).build()
        );
        List<PointDescription> result = List.of(
                PointDescription.builder().name("End").type(PointType.POINT).coordinate1(-1.0).coordinate2(2.0).build(),
                PointDescription.builder().name("Start").type(PointType.POINT).coordinate1(-5.0).coordinate2(2.0).build(),
                PointDescription.builder().name("Mid").type(PointType.MIDPOINT).coordinate1(-3.0).coordinate2(2.0).build()
        );
        Assertions.assertEquals(result, pointService.findCommonPointDescription(picture2, picture1));
        Assertions.assertEquals(pointService.findCommonPointDescription(picture1, picture2),
                pointService.findCommonPointDescription(picture2, picture1));
    }

    @Test
    void testCheckAnswer1() {
        List<PointDescription> result = List.of(
                PointDescription.builder().name("End").type(PointType.POINT).coordinate1(-1.0).coordinate2(2.0).build(),
                PointDescription.builder().name("Start").type(PointType.POINT).coordinate1(-5.0).coordinate2(2.0).build(),
                PointDescription.builder().name("Mid").type(PointType.MIDPOINT).coordinate1(-3.0).coordinate2(2.0).build()
        );
        Assertions.assertTrue(pointService.checkAnswer(picture1, result));
    }
    @Test
    void testCheckAnswer2() {
        List<PointDescription> result = List.of(
                PointDescription.builder().name("End").type(PointType.POINT).coordinate1(null).coordinate2(null).build(),
                PointDescription.builder().name("Start").type(PointType.POINT).coordinate1(null).coordinate2(null).build(),
                PointDescription.builder().name("Mid").type(PointType.MIDPOINT).coordinate1(-3.0).coordinate2(2.0).build()
        );
        Assertions.assertTrue(pointService.checkAnswer(picture1, result));
    }
    @Test
    void testCheckAnswer3() {
        List<PointDescription> result = List.of(
                PointDescription.builder().name(null).type(PointType.POINT).coordinate1(null).coordinate2(null).build(),
                PointDescription.builder().name(null).type(PointType.POINT).coordinate1(null).coordinate2(null).build(),
                PointDescription.builder().name(null).type(PointType.MIDPOINT).coordinate1(-3.0).coordinate2(2.0).build()
        );
        Assertions.assertTrue(pointService.checkAnswer(picture1, result));
    }
    @Test
    void testCheckAnswer4() {
        List<PointDescription> result = List.of(
                PointDescription.builder().name(null).type(PointType.POINT).coordinate1(null).coordinate2(null).build(),
                PointDescription.builder().name(null).type(PointType.POINT).coordinate1(null).coordinate2(null).build(),
                PointDescription.builder().name(null).type(PointType.MIDPOINT).coordinate1(null).coordinate2(null).build()
        );
        Assertions.assertTrue(pointService.checkAnswer(picture1, result));
    }
    @Test
    void testCheckAnswer5() {
        List<PointDescription> result = List.of(
                PointDescription.builder().name("FEnd").type(PointType.POINT).coordinate1(-1.0).coordinate2(2.0).build(),
                PointDescription.builder().name("FStart").type(PointType.POINT).coordinate1(-5.0).coordinate2(2.0).build(),
                PointDescription.builder().name("FMid").type(PointType.MIDPOINT).coordinate1(-3.0).coordinate2(2.0).build()
        );
        Assertions.assertFalse(pointService.checkAnswer(picture1, result));
    }
    @Test
    void testCheckAnswer6() {
        List<PointDescription> result = List.of(
                PointDescription.builder().name(null).type(PointType.POINT).coordinate1(1.0).coordinate2(1.0).build(),
                PointDescription.builder().name(null).type(PointType.POINT).coordinate1(2.0).coordinate2(2.0).build()
        );
        Assertions.assertFalse(pointService.checkAnswer(picture1, result));
    }
    @Test
    void testCheckAnswer7() {
        List<PointDescription> result = List.of(
                PointDescription.builder().name(null).type(PointType.POINT).coordinate1(null).coordinate2(null).build(),
                PointDescription.builder().name(null).type(PointType.POINT).coordinate1(null).coordinate2(null).build()
        );
        Assertions.assertTrue(pointService.checkAnswer(picture1, result));
    }
}