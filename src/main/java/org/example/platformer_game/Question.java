package org.example.platformer_game;
import java.util.ArrayList;
public class Question   {
    public Question() {
    }
    public ArrayList<Object[]> getQuestions(int level) {

        ArrayList<Object[]> LEVEL_ONE = new ArrayList<>();
        ArrayList<Object[]> LEVEL_TWO = new ArrayList<>();
        ArrayList<Object[]> LEVEL_THREE = new ArrayList<>();

        switch (level){
            case 1:
                //Algebra basic
                LEVEL_ONE.add(new Object[]{"Solve 4x−7(2−x) = 3 x + 2", "2" ,"next to one",1});
                LEVEL_ONE.add(new Object[]{"Solve 1/x = 1/5", "5" ,"Really?",2});
                LEVEL_ONE.add(new Object[]{"Solve for z: z - 5 = 6", "11","Bro...",3});
                LEVEL_ONE.add(new Object[]{"Solve for x: 3x – 5 = 10","5","Try Harder",4});
                LEVEL_ONE.add(new Object[]{"Solve for m: 2(m + 6) = 48","18","Legal Age",5});
                LEVEL_ONE.add(new Object[]{"Solve for y: 13(y–4)–3(y–9)–5(y+4)=0","9","multiply each term by a common factor,",6});
                LEVEL_ONE.add(new Object[]{"If (a−8)/3=(a−3)/2, then a = ?","-7","A negative number",7});
                LEVEL_ONE.add(new Object[]{"Solve for x: (x+2)(x+3)+(x–3)(x–2)–2x(x+1)=0","6","(x + a)(x + b) - 2x(x + c)",8});
                LEVEL_ONE.add(new Object[]{"Everyone's favorite number.","69","Your not Man enough",9});
                LEVEL_ONE.add(new Object[]{"Bonus","","Try Premium for $5/month",0});
                return LEVEL_ONE;
            case 2:
                //Geometry basic
                LEVEL_TWO.add(new Object[]{"What is the sum of the interior angles of a triangle?" ,"180","Half 360",1});
                LEVEL_TWO.add(new Object[]{"What shape is formed by four equal sides and all angles are right angles?" ,"square","Not a Rectangle",2});
                LEVEL_TWO.add(new Object[]{"What is the sum of the measures of all angles in a quadrilateral?" ,"360","twice of 180",3});
                LEVEL_TWO.add(new Object[]{"What is the sum of the measures of the angles in a hexagon?" ,"720","the side of the hexagon multiplied by 6",4});
                LEVEL_TWO.add(new Object[]{"What is the area of a square with side length 4 cm?" ,"16 cm^2","Side x side, and the unit of measurement cm^2",5});
                LEVEL_TWO.add(new Object[]{"What is the area of a triangle with base 6 cm and height 8 cm?" ,"24 cm^2","base X height, and the unit of measurement cm^2",6});
                LEVEL_TWO.add(new Object[]{"What do you call the half of a diameter?" ,"radius","Starts with 'r'",7});
                LEVEL_TWO.add(new Object[]{"What is the perimeter of a circle with radius 3 cm?" ,"18.85 cm","C = 2πr",8});
                LEVEL_TWO.add(new Object[]{"What is the minimum number of rays needed to form an angle?" ,"2","Peace sign",9});
                LEVEL_TWO.add(new Object[]{"What is the common endpoint of two rays that form an angle called?" ,"vertex","Starts with a 'v' ",0});
                return LEVEL_TWO;
            case 3:
                //Matrix
                LEVEL_THREE.add(new Object[]{"Find the Determinant: \n" +
                        "[ 1 2 ]\n" +
                        "[ 3 4 ]" ,"-2","No hint this time",1});
                LEVEL_THREE.add(new Object[]{"The determinant of a 3x3 matrix is a scalar value. True of False?" ,"True","No hint this time",2});
                LEVEL_THREE.add(new Object[]{"The _____ of a square matrix is a scalar value that can be used to determine\n" +
                        "a system of linear equations, as well as to find the inverse of a matrix." ,"determinant","No hint this time",3});
                LEVEL_THREE.add(new Object[]{"If two rows of a matrix are swapped, the determinant changes sign. True or False?" ,"True","No hint this time",4});
                LEVEL_THREE.add(new Object[]{"Find the Determinant: \n" +
                        "[ 3 8 ]\n" +
                        "[ 4 6 ]" ,"-14","Negative number",5});
                LEVEL_THREE.add(new Object[]{"Find the Determinant: \n" +
                        "[ 6  1  1 ]\n" +
                        "[ 4 -2  5 ]\n" +
                        "[ 2  8  7 ]" ,"-306","Negative number",6});
                LEVEL_THREE.add(new Object[]{"Find the Determinant: \n" +
                        "[ 1 -1  0 ]\n" +
                        "[ 2  3  4 ]\n" +
                        "[ 0  1  2 ]" ,"6","No hint this time",7});
                LEVEL_THREE.add(new Object[]{"Find the Determinant: \n" +
                        "[ 1  2  3  9 ]\n" +
                        "[ 4  5  8  6 ]\n" +
                        "[ 0  0  0  0 ]\n" +
                        "[ 7  1  8  9 ]" ,"0","Notice the 3rd row   ",8});
                LEVEL_THREE.add(new Object[]{"Find the Determinant: \n" +
                        "[ 4  3  2  2 ]\n" +
                        "[ 0  1 -3  3 ]\n" +
                        "[ 0 -1  3  3 ]\n" +
                        "[ 0  3  1  1 ]" ,"-240","No hint this time",9});
                LEVEL_THREE.add(new Object[]{"A _____ is a rectangular array of numbers arranged in rows and columns" ,"matrix","No hint this time",0});
                return LEVEL_THREE;
            default:
                return null;
        }
    }
}
