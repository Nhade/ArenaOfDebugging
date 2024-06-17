package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.util.ArrayList;

import static com.mygdx.game.Constant.PPM;

public class Obstacle {
    ArrayList<Vector2[]> verticesArray = new ArrayList<>();
    ArrayList<Vector2[]> boundaryVerticesArray = new ArrayList<>();

        Vector2[] vertices1 = new Vector2[]{
                new Vector2(1379, 2913),
                new Vector2(2371, 2946),
                new Vector2(2134, 3186),
                new Vector2(1237, 3174)
        };
        Vector2[] vertices2 = new Vector2[]{
                new Vector2(3126, 1266),
                new Vector2(2861, 1399),
                new Vector2(3126, 2156),
                new Vector2(2875, 2399)
        };
        Vector2[] vertices3A = new Vector2[]{
                new Vector2(3818, 1731),
                new Vector2(3761, 1451),
                new Vector2(4103, 1445),
                new Vector2(4115, 2115)
        };
        Vector2[] vertices3B = new Vector2[]{
                new Vector2(3818, 1731),
                new Vector2(4115, 2115),
                new Vector2(3797, 1945)
        };
        Vector2[] vertices4 = new Vector2[]{
                new Vector2(3472, 2782),
                new Vector2(3693, 2521),
                new Vector2(4210, 3009),
                new Vector2(3965, 3253)
        };
        Vector2[] vertices5A = new Vector2[]{
                new Vector2(1360, 3960),
                new Vector2(1492, 4178),
                new Vector2(2596, 4029),
                new Vector2(2023, 3580)
        };
        Vector2[] vertices5B = new Vector2[]{
                new Vector2(2596, 4029),
                new Vector2(3024, 4375),
                new Vector2(3450, 4196),
                new Vector2(2990, 3693)
        };
        Vector2[] vertices5C = new Vector2[]{
                new Vector2(2596, 4029),
                new Vector2(2990, 3693),
                new Vector2(2023, 3580)
        };
        Vector2[] vertices6 = new Vector2[]{
                new Vector2(1392, 4750),
                new Vector2(1727, 4696),
                new Vector2(1729, 5947),
                new Vector2(1381, 5960)
        };
        Vector2[] vertices7A = new Vector2[]{
                new Vector2(2250, 5441),
                new Vector2(2745, 5604),
                new Vector2(2802, 5912),
                new Vector2(2171, 5737)
        };
        Vector2[] vertices7B = new Vector2[]{
                new Vector2(2745, 5604),
                new Vector2(3159, 5441),
                new Vector2(3477, 5594),
                new Vector2(2802, 5912)
        };
        Vector2[] vertices7C = new Vector2[]{
                new Vector2(3159, 5441),
                new Vector2(3144, 4860),
                new Vector2(3477, 4905),
                new Vector2(3477, 5594)
        };
        Vector2[] vertices8A = new Vector2[]{
                new Vector2(4593, 5337),
                new Vector2(4252, 5775),
                new Vector2(3920, 5779),
                new Vector2(3914, 5367)
        }; 
        Vector2[] vertices8B = new Vector2[]{
                new Vector2(3819, 5029),
                new Vector2(4593, 5337),
                new Vector2(3914, 5367)
        }; 
        Vector2[] vertices8C = new Vector2[]{
                new Vector2(3819, 5029),
                new Vector2(3926, 4746),
                new Vector2(4593, 5337)
        };
        Vector2[] vertices9A = new Vector2[]{
                new Vector2(5023, 3925),
                new Vector2(5393, 4180),
                new Vector2(5132, 4447),
                new Vector2(4858, 4063)
        };
        Vector2[] vertices9B = new Vector2[]{
                new Vector2(4449, 3694),
                new Vector2(4686, 3484),
                new Vector2(5023, 3925),
                new Vector2(4858, 4063)
        };
        Vector2[] vertices10A = new Vector2[]{
                new Vector2(5467, 3290),
                new Vector2(6108, 3116),
                new Vector2(6388, 3293),
                new Vector2(5528, 3451)
        };
        Vector2[] vertices10B = new Vector2[]{
                new Vector2(6108, 3116),
                new Vector2(6388, 3293),
                new Vector2(6520, 2446),
                new Vector2(6188, 2581),
        };
        Vector2[] vertices10C = new Vector2[]{
                new Vector2(5609, 2111),
                new Vector2(6188, 2581),
                new Vector2(5544, 2388),
                new Vector2(6520, 2446)
        };
        Vector2[] vertices11A = new Vector2[]{
                new Vector2(4793, 1432),
                new Vector2(5613, 1471),
                new Vector2(5621, 1729),
                new Vector2(4790, 1809)
        };
        Vector2[] vertices11B = new Vector2[]{
                new Vector2(6256, 1424),
                new Vector2(6275, 1809),
                new Vector2(5621, 1729),
        };
        Vector2[] vertices12A = new Vector2[]{
                new Vector2(7359, 1828),
                new Vector2(7736, 1471),
                new Vector2(8065, 1836)
        };
        Vector2[] vertices12B = new Vector2[]{
                new Vector2(7736, 1471),
                new Vector2(8686, 1620),
                new Vector2(8588, 2146),
                new Vector2(8065, 1836)
        };
        Vector2[] vertices13A = new Vector2[]{
                new Vector2(7032, 3784),
                new Vector2(7643, 2928),
                new Vector2(7834, 3246),
                new Vector2(7296, 3908)
        };
        Vector2[] vertices13B = new Vector2[]{
                new Vector2(7643, 2928),
                new Vector2(8578, 2820),
                new Vector2(8597, 3113),
                new Vector2(7834, 3246)
        };
        Vector2[] vertices14A = new Vector2[]{
                new Vector2(5801, 5118),
                new Vector2(5614, 4700),
                new Vector2(6108, 4563)
        };
        Vector2[] vertices14B = new Vector2[]{
                new Vector2(6634, 4227),
                new Vector2(6732, 4375),
                new Vector2(5801, 5118),
                new Vector2(6108, 4563)
        };
        Vector2[] vertices14C = new Vector2[]{
                new Vector2(6274, 4123),
                new Vector2(6634, 4227),
                new Vector2(6108, 4563)
        };
        Vector2[] vertices15A = new Vector2[]{
                new Vector2(4121, 6281),
                new Vector2(4531, 5999),
                new Vector2(4762, 6141),
                new Vector2(4459, 6490)
        };
        Vector2[] vertices15B = new Vector2[]{
                new Vector2(4531, 5999),
                new Vector2(4838, 5570),
                new Vector2(4932, 5705)
        };
        Vector2[] vertices15C = new Vector2[]{
                new Vector2(4531, 5999),
                new Vector2(4932, 5705),
                new Vector2(5111, 5851),
                new Vector2(4762, 6141)
        };
        Vector2[] vertices16A = new Vector2[]{
                new Vector2(3992, 6869),
                new Vector2(3784, 7002),
                new Vector2(3338, 6369),
                new Vector2(3427, 6169)
        };
        Vector2[] vertices16B = new Vector2[]{
                new Vector2(3338, 6369),
                new Vector2(2830, 6512),
                new Vector2(2678, 6337),
                new Vector2(3427, 6169)
        };
        Vector2[] vertices16C = new Vector2[]{
                new Vector2(2830, 6512),
                new Vector2(2678, 6337),
                new Vector2(2481, 6988),
                new Vector2(2714, 6972)
        };
        Vector2[] vertices16D = new Vector2[]{
                new Vector2(2481, 6988),
                new Vector2(3011, 7593),
                new Vector2(3181, 7496),
                new Vector2(2714, 6972)
        };
        Vector2[] vertices17A = new Vector2[]{
                new Vector2(1708, 6604),
                new Vector2(1716, 7099),
                new Vector2(1391, 7302),
                new Vector2(1437, 6680)
        };
        Vector2[] vertices17B = new Vector2[]{
                new Vector2(1716, 7099),
                new Vector2(2183, 7266),
                new Vector2(1967, 7475),
                new Vector2(1391, 7302)
        };
        Vector2[] vertices17C = new Vector2[]{
                new Vector2(2183, 7266),
                new Vector2(2197, 7445),
                new Vector2(1967, 7475),
        };
        Vector2[] vertices18A = new Vector2[]{
                new Vector2(2209, 7711),
                new Vector2(2476, 8007),
                new Vector2(1930, 8358),
                new Vector2(1410, 7855)
        };
        Vector2[] vertices18B = new Vector2[]{
                new Vector2(1930, 8358),
                new Vector2(1378, 8338),
                new Vector2(1410, 7855)
        };
        Vector2[] vertices19A = new Vector2[]{
                new Vector2(9338, 1587),
                new Vector2(9741, 1921),
                new Vector2(9213, 2005)
        };
        Vector2[] vertices19B = new Vector2[]{
                new Vector2(9741, 1921),
                new Vector2(9729, 2324),
                new Vector2(9523, 2434),
                new Vector2(9213, 2005)
        };
        Vector2[] vertices20A = new Vector2[]{
                new Vector2(10020, 3107),
                new Vector2(9992, 3609),
                new Vector2(9637, 3590),
                new Vector2(9531, 3136)
        };
        Vector2[] vertices20B = new Vector2[]{
                new Vector2(9637, 3590),
                new Vector2(9283, 3757),
                new Vector2(9029, 3496),
                new Vector2(9531, 3136)
        };
        Vector2[] vertices21A = new Vector2[]{
                new Vector2(10042, 3992),
                new Vector2(10003, 4611),
                new Vector2(9870, 4603),
                new Vector2(9683, 3979)
        };
        Vector2[] vertices21B = new Vector2[]{
                new Vector2(10017, 5153),
                new Vector2(9625, 5208),
                new Vector2(9870, 4603),
                new Vector2(10003, 4611),
        };
        Vector2[] vertices22A = new Vector2[]{
                new Vector2(7391, 4541),
                new Vector2(7565, 4500),
                new Vector2(7730, 5147),
                new Vector2(7508, 5252)
        };
        Vector2[] vertices22B = new Vector2[]{
                new Vector2(8207, 5518),
                new Vector2(8179, 5296),
                new Vector2(7730, 5147),
                new Vector2(7508, 5252),
        };
        Vector2[] vertices22C = new Vector2[]{
                new Vector2(8982, 4985),
                new Vector2(8207, 5518),
                new Vector2(8179, 5296),
                new Vector2(8813, 4876)
        };
        Vector2[] vertices22D = new Vector2[]{
                new Vector2(9055, 4416),
                new Vector2(8982, 4985),
                new Vector2(8813, 4876),
                new Vector2(8829, 4488)
        };
        Vector2[] vertices22E = new Vector2[]{
                new Vector2(8328, 3927),
                new Vector2(9055, 4416),
                new Vector2(8829, 4488),
                new Vector2(8167, 4072)
        };
        Vector2[] vertices23A = new Vector2[]{
                new Vector2(6916, 4985),
                new Vector2(7119, 5297),
                new Vector2(6871, 5542),
                new Vector2(6739, 5326)
        };
        Vector2[] vertices23B = new Vector2[]{
                new Vector2(6871, 5542),
                new Vector2(6601, 5877),
                new Vector2(6292, 5603),
                new Vector2(6739, 5326)
        };
        Vector2[] vertices24A = new Vector2[]{
                new Vector2(5613, 6334),
                new Vector2(5437, 6782),
                new Vector2(5210, 6694)
        };
        Vector2[] vertices24B = new Vector2[]{
                new Vector2(5613, 6334),
                new Vector2(6022, 6743),
                new Vector2(5772, 6848)
        };
        Vector2[] vertices24C = new Vector2[]{
                new Vector2(5613, 6334),
                new Vector2(5772, 6848),
                new Vector2(5554, 7149),
                new Vector2(5437, 6782),
        };
        Vector2[] vertices25A = new Vector2[]{
                new Vector2(4672, 7139),
                new Vector2(4945, 7336),
                new Vector2(4317, 8233),
                new Vector2(4093, 7816)
        };
        Vector2[] vertices25B = new Vector2[]{
                new Vector2(4317, 8233),
                new Vector2(3510, 8338),
                new Vector2(3473, 7986),
                new Vector2(4093, 7816)
        };
        Vector2[] vertices26A = new Vector2[]{
                new Vector2(2609, 8509),
                new Vector2(2737, 8710),
                new Vector2(2500, 9076),
                new Vector2(1831, 8997)
        };
        Vector2[] vertices26B = new Vector2[]{
                new Vector2(2171, 9295),
                new Vector2(1589, 9446),
                new Vector2(1831, 8997)
        };
        Vector2[] vertices26C = new Vector2[]{
                new Vector2(2500, 9076),
                new Vector2(2171, 9295),
                new Vector2(1831, 8997)
        };
        Vector2[] vertices26D = new Vector2[]{
                new Vector2(2171, 9295),
                new Vector2(2322, 9722),
                new Vector2(2065, 9884),
                new Vector2(1589, 9446)
        };
        Vector2[] vertices27A = new Vector2[]{
                new Vector2(9976, 5551),
                new Vector2(10005,5986),
                new Vector2(9788, 6016),
                new Vector2(9671, 5566)
        };
        Vector2[] vertices27B = new Vector2[]{
                new Vector2(10005,5986),
                new Vector2(10037,6964),
                new Vector2(9731, 6998),
                new Vector2(9788, 6016)
        };
        Vector2[] vertices28A = new Vector2[]{
                new Vector2(9421, 6031),
                new Vector2(9304, 6337),
                new Vector2(8802, 6038),
                new Vector2(8825, 5695)
        };
        Vector2[] vertices28B = new Vector2[]{
                new Vector2(8802, 6038),
                new Vector2(8454, 6329),
                new Vector2(8213, 6118),
                new Vector2(8825, 5695)
        };
        Vector2[] vertices28C = new Vector2[]{
                new Vector2(8454, 6329),
                new Vector2(8364, 6820),
                new Vector2(8065, 6760),
                new Vector2(8213, 6118)
        };
        Vector2[] vertices29A = new Vector2[]{
                new Vector2(7666, 5748),
                new Vector2(7689, 6480),
                new Vector2(7172, 5744)
        };
        Vector2[] vertices29B = new Vector2[]{
                new Vector2(7689, 6480),
                new Vector2(6919, 5970),
                new Vector2(7172, 5744)
        };
        Vector2[] vertices29C = new Vector2[]{
                new Vector2(7689, 6480),
                new Vector2(7017, 6273),
                new Vector2(6919, 5970)
        };
        Vector2[] vertices29D = new Vector2[]{  
                new Vector2(7689, 6480),
                new Vector2(7444, 6771),
                new Vector2(7017, 6273)   
        };
        Vector2[] vertices30A = new Vector2[]{
                new Vector2(6261, 7003),
                new Vector2(6566, 7377),
                new Vector2(6408, 7626),
                new Vector2(5996, 7286)
        };
        Vector2[] vertices30B = new Vector2[]{
                new Vector2(6566, 7377),
                new Vector2(7008, 7717),
                new Vector2(6763, 7992),
                new Vector2(6408, 7626)
        };
        Vector2[] vertices31A = new Vector2[]{
                new Vector2(5140, 8905),
                new Vector2(5710, 9264),
                new Vector2(5578, 9509),
                new Vector2(4807, 8935)
        };
        Vector2[] vertices31B = new Vector2[]{
                new Vector2(6031, 8184),
                new Vector2(6046, 8418),
                new Vector2(5382, 8410),
                new Vector2(5166, 8154)
        };
        Vector2[] vertices31C = new Vector2[]{
                new Vector2(5382, 8410),
                new Vector2(5140, 8905),
                new Vector2(4807, 8935),
                new Vector2(5166, 8154)
        };
        Vector2[] vertices32A = new Vector2[]{
                new Vector2(4439, 9670),
                new Vector2(4271, 10098),
                new Vector2(3992, 9856),
                new Vector2(4132, 9618)
        };
        Vector2[] vertices32B = new Vector2[]{
                new Vector2(4271, 10098),
                new Vector2(3527, 10110),
                new Vector2(3639, 9831),
                new Vector2(3992, 9856)
        };
        Vector2[] vertices32C = new Vector2[]{
                new Vector2(3527, 10110),
                new Vector2(3236, 9658),
                new Vector2(3475, 9568),
                new Vector2(3639, 9831)
        };
        Vector2[] vertices33A = new Vector2[]{
                new Vector2(6649, 9695),
                new Vector2(6652, 10076),
                new Vector2(5747, 10113),
                new Vector2(5769, 9869)
        };
        Vector2[] vertices33B = new Vector2[]{
                new Vector2(5747, 10113),
                new Vector2(5133, 10076),
                new Vector2(5167, 9748),
                new Vector2(5769, 9869)
        };
        Vector2[] vertices34 = new Vector2[]{
                new Vector2(7326, 9335),
                new Vector2(7673, 9490),
                new Vector2(7642, 10020),
                new Vector2(7283, 10058)
        };
        Vector2[] vertices35 = new Vector2[]{
                new Vector2(7456, 8199),
                new Vector2(7977, 8695),
                new Vector2(7763, 8992),
                new Vector2(7215, 8425)
        };
        Vector2[] vertices36A = new Vector2[]{
                new Vector2(9545, 7548),
                new Vector2(9537, 7858),
                new Vector2(9257, 7415)
        };
        Vector2[] vertices36B = new Vector2[]{
                new Vector2(9537, 7858),
                new Vector2(8751, 8054),
                new Vector2(8787, 7586),
                new Vector2(9257, 7415)
        };
        Vector2[] vertices36C = new Vector2[]{
                new Vector2(8751, 8054),
                new Vector2(8115, 7393),
                new Vector2(8632, 7224),
                new Vector2(8787, 7586)
        };
        Vector2[] vertices37 = new Vector2[]{
                new Vector2(10275, 8298),
                new Vector2(10217, 8538),
                new Vector2(9207, 8550),
                new Vector2(9448, 8295)
        };
        Vector2[] vertices38 = new Vector2[]{
                new Vector2(8533, 9203),
                new Vector2(8538, 10210),
                new Vector2(8289, 10277),
                new Vector2(8267, 9449)
        };
        Vector2[] Bvertices1 = new Vector2[]{
                new Vector2(1597, 432),
                new Vector2(1086, 251)
        };
        Vector2[] Bvertices2 = new Vector2[]{
                new Vector2(1086, 251),
                new Vector2(474, 449)
        };
        Vector2[] Bvertices3 = new Vector2[]{
                new Vector2(474, 449),
                new Vector2(281, 1079)
        };
        Vector2[] Bvertices4 = new Vector2[]{
                new Vector2(281, 1079),
                new Vector2(474, 1527)
        };
        Vector2[] Bvertices5 = new Vector2[]{
                new Vector2(474, 1527),
                new Vector2(481, 9883)
        };
        Vector2[] Bvertices6 = new Vector2[]{
                new Vector2(481, 9883),
                new Vector2(1590, 10976)
        };
        Vector2[] Bvertices7 = new Vector2[]{
                new Vector2(1590, 10976),
                new Vector2(10022, 10970)
        };
        Vector2[] Bvertices8 = new Vector2[]{
                new Vector2(10022, 10970),
                new Vector2(10519, 11151)
        };
        Vector2[] Bvertices9 = new Vector2[]{
                new Vector2(10519, 11151),
                new Vector2(11131, 10968)
        };
        Vector2[] Bvertices10 = new Vector2[]{
                new Vector2(11131, 10968),
                new Vector2(11327, 10346)
        };
        Vector2[] Bvertices11 = new Vector2[]{
                new Vector2(11327, 10346),
                new Vector2(11132, 9926)
        };
        Vector2[] Bvertices12 = new Vector2[]{
                new Vector2(11132, 9926),
                new Vector2(11145, 1599)
        };
        Vector2[] Bvertices13 = new Vector2[]{
                new Vector2(11145, 1599),
                new Vector2(9981, 449)
        };
        Vector2[] Bvertices14 = new Vector2[]{
                new Vector2(9981, 449),
                new Vector2(1597, 432)
        };

    public Obstacle(World world) {
        verticesArray.add(vertices1);
        verticesArray.add(vertices2);
        verticesArray.add(vertices3A);
        verticesArray.add(vertices3B);
        verticesArray.add(vertices4);
        verticesArray.add(vertices5A);
        verticesArray.add(vertices5B);
        verticesArray.add(vertices5C);
        verticesArray.add(vertices6);
        verticesArray.add(vertices7A);
        verticesArray.add(vertices7B);
        verticesArray.add(vertices7C);
        verticesArray.add(vertices8A);
        verticesArray.add(vertices8B);
        verticesArray.add(vertices8C);
        verticesArray.add(vertices9A);
        verticesArray.add(vertices9B);
        verticesArray.add(vertices10A);
        verticesArray.add(vertices10B);
        verticesArray.add(vertices10C);
        verticesArray.add(vertices11A);
        verticesArray.add(vertices11B);
        verticesArray.add(vertices12A);
        verticesArray.add(vertices12B);
        verticesArray.add(vertices13A);
        verticesArray.add(vertices13B);
        verticesArray.add(vertices14A);
        verticesArray.add(vertices14B);
        verticesArray.add(vertices14C);
        verticesArray.add(vertices15A);
        verticesArray.add(vertices15B);
        verticesArray.add(vertices15C);
        verticesArray.add(vertices16A);
        verticesArray.add(vertices16B);
        verticesArray.add(vertices16C);
        verticesArray.add(vertices16D);
        verticesArray.add(vertices17A);
        verticesArray.add(vertices17B);
        verticesArray.add(vertices17C);
        verticesArray.add(vertices18A);
        verticesArray.add(vertices18B);
        verticesArray.add(vertices19A);
        verticesArray.add(vertices19B);
        verticesArray.add(vertices20A);
        verticesArray.add(vertices20B);
        verticesArray.add(vertices21A);
        verticesArray.add(vertices21B);
        verticesArray.add(vertices22A);
        verticesArray.add(vertices22B);
        verticesArray.add(vertices22C);
        verticesArray.add(vertices22D);
        verticesArray.add(vertices22E);
        verticesArray.add(vertices23A);
        verticesArray.add(vertices23B);
        verticesArray.add(vertices24A);
        verticesArray.add(vertices24B);
        verticesArray.add(vertices24C);
        verticesArray.add(vertices25A);
        verticesArray.add(vertices25B);
        verticesArray.add(vertices26A);
        verticesArray.add(vertices26B);
        verticesArray.add(vertices26C);
        verticesArray.add(vertices26D);
        verticesArray.add(vertices27A);
        verticesArray.add(vertices27B);
        verticesArray.add(vertices28A);
        verticesArray.add(vertices28B);
        verticesArray.add(vertices28C);
        verticesArray.add(vertices29A);
        verticesArray.add(vertices29B);
        verticesArray.add(vertices29C);
        verticesArray.add(vertices29D);
        verticesArray.add(vertices30A);
        verticesArray.add(vertices30B);
        verticesArray.add(vertices31A);
        verticesArray.add(vertices31B);
        verticesArray.add(vertices31C);
        verticesArray.add(vertices32A);
        verticesArray.add(vertices32B);
        verticesArray.add(vertices32C);
        verticesArray.add(vertices33A);
        verticesArray.add(vertices33B);
        verticesArray.add(vertices34);
        verticesArray.add(vertices35);
        verticesArray.add(vertices36A);
        verticesArray.add(vertices36B);
        verticesArray.add(vertices36C);
        verticesArray.add(vertices37);
        verticesArray.add(vertices38);

        boundaryVerticesArray.add(Bvertices1);
        boundaryVerticesArray.add(Bvertices2);
        boundaryVerticesArray.add(Bvertices3);
        boundaryVerticesArray.add(Bvertices4);
        boundaryVerticesArray.add(Bvertices5);
        boundaryVerticesArray.add(Bvertices6);
        boundaryVerticesArray.add(Bvertices7);
        boundaryVerticesArray.add(Bvertices8);
        boundaryVerticesArray.add(Bvertices9);
        boundaryVerticesArray.add(Bvertices10);
        boundaryVerticesArray.add(Bvertices11);
        boundaryVerticesArray.add(Bvertices12);
        boundaryVerticesArray.add(Bvertices13);
        boundaryVerticesArray.add(Bvertices14);


        for (Vector2[] vertices : verticesArray) {
            Vector2[] convertedVertices = new Vector2[vertices.length];
            for (int i = 0; i < vertices.length; i++) {
                convertedVertices[i] = vertices[i].scl(1 / PPM);
            }
            // Create and configure body
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set(0, 0);

            Body body = world.createBody(bodyDef);

            // Create and configure fixture (collision functionality)
            PolygonShape poly = new PolygonShape();
            poly.set(convertedVertices);

            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = poly;
            fixtureDef.density = 1.0f;
            fixtureDef.friction = 0.5f;
            fixtureDef.restitution = 0.5f;

            body.createFixture(fixtureDef);
            poly.dispose();
        }

        for (Vector2[] vertices : boundaryVerticesArray) {
            Vector2[] boundaryConvertedVertices = new Vector2[vertices.length];
            for (int i = 0; i < vertices.length; i++) {
                boundaryConvertedVertices[i] = vertices[i].scl(1 / PPM);
            }
            // Create and configure body
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set(0, 0);

            Body body = world.createBody(bodyDef);

            // Create and configure fixture (collision functionality)
            EdgeShape edge = new EdgeShape();
            edge.set(boundaryConvertedVertices[0], boundaryConvertedVertices[1]);

            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = edge;
            fixtureDef.density = 1.0f;
            fixtureDef.friction = 0.5f;
            fixtureDef.restitution = 0.5f;

            body.createFixture(fixtureDef);
            edge.dispose();
        }
    }
}
