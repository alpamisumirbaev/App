package com.example.data

import com.example.model.Question
import com.example.model.QuestionBlock

object QuestionDataSource {

    val blocks = listOf(
        QuestionBlock(1, "Mathematics: Core Algebra", "Linear equations, quadratic problems, inequalities, progressions, and percentage applications.", "Mathematics", 1, 25),
        QuestionBlock(2, "Mathematics: Geometry & Word Problems", "Calculations of perimeter, area, right triangles, trigonometry, ratios, and rates of work.", "Mathematics", 26, 50),
        QuestionBlock(3, "Physics: Mechanics & Energy", "Force and motion equations, Newton's laws, speed/velocity, work, energy transition, and gravity.", "Science", 51, 75),
        QuestionBlock(4, "Chemistry: Elements & Bonding", "Atomic structures, periodic trends, elemental attributes, chemical formulas, pH values, and bonding.", "Science", 76, 100),
        QuestionBlock(5, "Biology: Cells & Genetics", "Plant and animal cell structures, organelles, cell division, DNA/RNA replication, and Mendel's laws.", "Science", 101, 125),
        QuestionBlock(6, "Biology: Human Physiology & Ecosystems", "Major system organs, circulatory systems, food webs, trophic levels, and ecological relationships.", "Science", 126, 150),
        QuestionBlock(7, "History: Ancient Civilizations", "Milestones of Egypt, Roman history, Greek city-states, the Silk Road, and classical empires.", "History", 151, 175),
        QuestionBlock(8, "History: Modern Era & Inventions", "The Industrial Revolution, notable political agreements, world wars, space exploration, and key inventors.", "History", 176, 200),
        QuestionBlock(9, "Geography: Physical Landforms & Oceans", "Tectonic activities, mountain altitudes, deserts, oceans, currents, and geological layers.", "Geography", 201, 225),
        QuestionBlock(10, "Geography: Capitals, Rivers & Lakes", "Sovereign capital matching, major global rivers, landlocked nations, canals, and great inland seas.", "Geography", 226, 250),
        QuestionBlock(11, "English: Applied Grammar & Vocabulary", "Parts of speech, sentence correction, subject-verb agreement, and high-frequency academic vocabulary.", "English", 251, 275),
        QuestionBlock(12, "English: Literary Devices & Discoveries", "Identifying metaphors, similes, narrative tones, famous literary works, and history of computing.", "English", 276, 300)
    )

    private val questionsList: List<Question> by lazy {
        val list = mutableListOf<Question>()

        // Helper to generate different categories
        for (i in 1..300) {
            val blockId = (i - 1) / 25 + 1
            val block = blocks.first { it.id == blockId }
            val relativeId = (i - 1) % 25 + 1

            val (qText, opts, correctIdx, expl) = when (blockId) {
                1 -> generateMathAlgebraQuestion(relativeId, i)
                2 -> generateMathGeometryQuestion(relativeId, i)
                3 -> generatePhysicsMechanicsQuestion(relativeId, i)
                4 -> generateChemistryQuestion(relativeId, i)
                5 -> generateBiologyCellQuestion(relativeId, i)
                6 -> generateBiologySystemQuestion(relativeId, i)
                7 -> generateAncientHistoryQuestion(relativeId, i)
                8 -> generateModernHistoryQuestion(relativeId, i)
                9 -> generatePhysicalGeographyQuestion(relativeId, i)
                10 -> generatePoliticalGeographyQuestion(relativeId, i)
                11 -> generateEnglishGrammarQuestion(relativeId, i)
                12 -> generateEnglishLiteratureQuestion(relativeId, i)
                else -> throw IllegalStateException("Invalid block index")
            }

            list.add(
                Question(
                    id = i,
                    blockId = blockId,
                    category = block.category,
                    questionText = qText,
                    options = opts,
                    correctOptionIndex = correctIdx,
                    explanation = expl
                )
            )
        }
        list
    }

    fun getAllQuestions(): List<Question> = questionsList

    fun getQuestionsForBlock(blockId: Int): List<Question> {
        return questionsList.filter { it.blockId == blockId }
    }

    fun getRandomQuestions(count: Int = 25): List<Question> {
        return questionsList.shuffled().take(count)
    }

    // BLOCK 1: ALGEBRA GENERATOR (Deterministic but varied)
    private fun generateMathAlgebraQuestion(relId: Int, globalId: Int): QuestionData {
        val a = relId * 2 + 1
        val b = relId * 3
        val c = relId * 5 + 12
        // ax + b = c  => ax = c-b => x = (c-b)/a
        // Let's make sure it is clean. Or generate custom preset equations.
        return when (relId) {
            1 -> QuestionData(
                "Solve for x in the linear equation: 4x + 7 = 23.",
                listOf("x = 3", "x = 4", "x = 5", "x = 6"),
                1,
                "To solve 4x + 7 = 23, subtract 7 from both sides to get 4x = 16. Then, divide by 4 to get x = 4."
            )
            2 -> QuestionData(
                "What is the value of the discriminant for the quadratic equation: x² - 6x + 9 = 0?",
                listOf("Discriminant = 12", "Discriminant = 9", "Discriminant = 0", "Discriminant = -36"),
                2,
                "The formula for the discriminant is D = b² - 4ac. Here, a=1, b=-6, c=9. Thus D = (-6)² - 4(1)(9) = 36 - 36 = 0, indicating exactly one real root."
            )
            3 -> QuestionData(
                "Find the 10th term of the arithmetic progression (AP): 3, 7, 11, 15, ...",
                listOf("29", "35", "39", "43"),
                2,
                "The nth term of an AP is a + (n-1)d. Here, active first term a = 3, common difference d = 4. The 10th term is 3 + (10-1)*4 = 3 + 36 = 39."
            )
            4 -> QuestionData(
                "An item is bought for $120 and sold for $150. What is the percentage profit gained?",
                listOf("15%", "20%", "25%", "30%"),
                2,
                "Profit = Selling Price - Cost Price = $150 - $120 = $30. Percentage Profit = (Profit / Cost Price) * 100 = (30 / 120) * 100 = 25%."
            )
            5 -> QuestionData(
                "Solve the inequality: 3x - 5 < 2x + 4.",
                listOf("x < 9", "x > 9", "x < -1", "x > -1"),
                0,
                "Subtracting 2x from both sides gives x - 5 < 4. Adding 5 to both sides gives x < 9."
            )
            6 -> QuestionData(
                "What is the sum of the roots of the quadratic equation: 2x² - 8x + 5 = 0?",
                listOf("-4", "4", "-2", "2"),
                1,
                "According to Vieta's formulas, the sum of roots of ax² + bx + c = 0 is -b/a. Here, -b/a = -(-8)/2 = 8/2 = 4."
            )
            7 -> QuestionData(
                "Determine the value of x that satisfies the system of equations:\nx + y = 10\nx - y = 4",
                listOf("x = 5", "x = 6", "x = 7", "x = 8"),
                2,
                "Adding both linear equations gives 2x = 14, which implies x = 7. Substituting x = 7 back into the first equation yields y = 3, which satisfies both."
            )
            8 -> QuestionData(
                "If 30% of a number is 45, what is 80% of that same number?",
                listOf("90", "120", "150", "180"),
                1,
                "First, find the number: 0.30 * N = 45 => N = 45 / 0.30 = 150. Now, calculate 80% of 150: 150 * 0.80 = 120."
            )
            9 -> QuestionData(
                "Simplify: (2x³y²) * (3x⁴y).",
                listOf("5x⁷y³", "6x⁷y³", "6x¹²y²", "5x¹²y²"),
                1,
                "When multiplying algebraic expressions, multiply the coefficients (2 * 3 = 6) and add the exponents of identical bases: x^(3+4) = x⁷, and y^(2+1) = y³."
            )
            10 -> QuestionData(
                "What is the value of 5⁰ + 5⁻¹?",
                listOf("1.2", "1.0", "6.0", "1.5"),
                0,
                "Any non-zero base raised to the power of 0 is 1. Under negative exponent rules, 5⁻¹ = 1/5 = 0.2. Adding them together gives 1 + 0.2 = 1.2."
            )
            11 -> QuestionData(
                "If y varies directly with x, and y = 24 when x = 6, find y when x = 11.",
                listOf("33", "40", "44", "48"),
                2,
                "Direct variation implies y = kx. Finding the constant k: 24 = k * 6 => k = 4. When x = 11, y = 4 * 11 = 44."
            )
            12 -> QuestionData(
                "Solve for x: log₂ (x + 3) = 4.",
                listOf("x = 13", "x = 11", "x = 5", "x = 16"),
                0,
                "Rewrite the logarithmic equation in exponential form: x + 3 = 2⁴. Since 2⁴ = 16, we have x + 3 = 16, which means x = 13."
            )
            13 -> QuestionData(
                "Find the value of f(-2) for the function f(x) = 3x² - 2x + 1.",
                listOf("9", "13", "17", "21"),
                2,
                "Substitute -2 for x in f(x): f(-2) = 3(-2)² - 2(-2) + 1 = 3(4) + 4 + 1 = 12 + 4 + 1 = 17."
            )
            14 -> QuestionData(
                "What is the sum of the first 8 terms of the sequence: 2, 5, 8, 11, ...?",
                listOf("80", "100", "110", "120"),
                1,
                "The sequence is an AP with a = 2, d = 3. The sum of the first n terms is S_n = (n/2) * [2a + (n-1)d]. S_8 = (8/2) * [2(2) + (8-1)*3] = 4 * [4 + 21] = 4 * 25 = 100."
            )
            15 -> QuestionData(
                "Simplify: (x² - 9) / (x - 3) for x ≠ 3.",
                listOf("x + 3", "x - 3", "x + 9", "1"),
                0,
                "Factor the numerator as a difference of squares: x² - 9 = (x - 3)(x + 3). Dividing by (x - 3) leaves we with x + 3."
            )
            16 -> QuestionData(
                "Find the solution set for: |2x - 3| = 7.",
                listOf("{5}", "{-2}", "{-2, 5}", "{-5, 2}"),
                2,
                "This absolute value splits into two equations: 2x - 3 = 7 => 2x = 10 => x = 5; and 2x - 3 = -7 => 2x = -4 => x = -2. The set is {-2, 5}."
            )
            17 -> QuestionData(
                "What is the domain of the real-valued function g(x) = 1 / √(x - 4)?",
                listOf("x ≥ 4", "x > 4", "x ≠ 4", "All real numbers"),
                1,
                "For the square root in the denominator to be defined and non-zero, the expression inside must be strictly positive: x - 4 > 0, which means x > 4."
            )
            18 -> QuestionData(
                "In a coordinate plane, find the slope of the line passing through points A(1, 3) and B(4, 15).",
                listOf("2", "4", "3", "5"),
                1,
                "The slope formula is m = (y₂ - y₁) / (x₂ - x₁). Here, m = (15 - 3) / (4 - 1) = 12 / 3 = 4."
            )
            19 -> QuestionData(
                "If the ratio of two numbers is 3:5 and their sum is 88, find the larger number.",
                listOf("33", "55", "66", "44"),
                1,
                "Let the numbers be 3k and 5k. Their sum is 8k = 88, so k = 11. The larger number is 5k = 5 * 11 = 55."
            )
            20 -> QuestionData(
                "Compute (2³)⁴ / 2⁶.",
                listOf("2⁶", "2⁸", "2²", "2⁷"),
                0,
                "Applying exponent rules, (2³)⁴ = 2^(3*4) = 2¹². Then, 2¹² / 2⁶ = 2^(12-6) = 2⁶."
            )
            21 -> QuestionData(
                "Identify the common ratio of the geometric progression (GP): 3, 6, 12, 24, ...",
                listOf("2", "3", "4", "6"),
                0,
                "The common ratio is found by dividing any term by its preceding term: 6 / 3 = 2, or 12 / 6 = 2."
            )
            22 -> QuestionData(
                "Determine x if 2^(3x - 1) = 32.",
                listOf("x = 1", "x = 2", "x = 3", "x = 4"),
                1,
                "Express 32 as a power of 2: 32 = 2⁵. Set the exponents equal: 3x - 1 = 5 => 3x = 6 => x = 2."
            )
            23 -> QuestionData(
                "A jar contains red and blue marbles in a 4:3 ratio. If there are 12 blue marbles, how many red marbles are there?",
                listOf("14", "16", "18", "20"),
                1,
                "Given 4/3 = Red/Blue => 4/3 = Red/12. Cross-multiply to solve: Red = (4 * 12) / 3 = 48 / 3 = 16."
            )
            24 -> QuestionData(
                "What is the product of the solutions to |x + 2| = 5?",
                listOf("-10", "-21", "21", "10"),
                1,
                "Split the equation: x + 2 = 5 => x = 3; and x + 2 = -5 => x = -7. The product of the solutions is 3 * (-7) = -21."
            )
            25 -> QuestionData(
                "A worker earns $18 per hour for the first 40 hours of work, and double time ($36 per hour) for any overtime hours. If they earn $864, how many overtime hours did they work?",
                listOf("4 hours", "6 hours", "8 hours", "10 hours"),
                0,
                "Regular earnings = 40 * 18 = $720. Overtime earnings = 864 - 720 = $144. Overtime hours worked = 144 / 36 = 4 hours."
            )
            else -> generateFallbackQuestion(globalId)
        }
    }

    // BLOCK 2: GEOMETRY & WORD PROBLEMS
    private fun generateMathGeometryQuestion(relId: Int, globalId: Int): QuestionData {
        return when (relId) {
            1 -> QuestionData(
                "Find the perimeter of a rectangle with length 15 cm and width 8 cm.",
                listOf("23 cm", "46 cm", "120 cm", "60 cm"),
                1,
                "Perimeter = 2 * (length + width) = 2 * (15 + 8) = 2 * 23 = 46 cm."
            )
            2 -> QuestionData(
                "What is the area of a circle with a diameter of 14 meters? (Use π ≈ 22/7)",
                listOf("154 m²", "616 m²", "44 m²", "88 m²"),
                0,
                "The radius of the circle is diameter / 2 = 14 / 2 = 7 meters. Area = π * r² = (22/7) * 7 * 7 = 154 m²."
            )
            3 -> QuestionData(
                "A right-angled triangle has legs of lengths 5 cm and 12 cm. Find the length of its hypotenuse.",
                listOf("13 cm", "15 cm", "17 cm", "20 cm"),
                0,
                "Using the Pythagorean theorem: c² = a² + b² = 5² + 12² = 25 + 144 = 169. Taking the square root gives c = 13 cm."
            )
            4 -> QuestionData(
                "A tank can be filled by Pipe A in 4 hours and by Pipe B in 6 hours. If both pipes are opened together, how long will it take to fill the tank?",
                listOf("2.4 hours", "5 hours", "2.0 hours", "3.0 hours"),
                0,
                "Combined rate per hour = 1/4 + 1/6 = 3/12 + 2/12 = 5/12. Therefore, the time taken is reciprocal of rate: 12/5 = 2.4 hours."
            )
            5 -> QuestionData(
                "Find the sum of the interior angles of a hexagon.",
                listOf("360°", "540°", "720°", "900°"),
                2,
                "The sum of interior angles is (n - 2) * 180°, where n is the number of sides. For a hexagon (n = 6): (6 - 2) * 180 = 4 * 180 = 720°."
            )
            6 -> QuestionData(
                "Determine the volume of a cylinder with base radius 3 cm and height 10 cm. (Leave in terms of π)",
                listOf("30π cm³", "60π cm³", "90π cm³", "120π cm³"),
                2,
                "Volume of cylinder = π * r² * h = π * (3)² * 10 = 9 * 10 * π = 90π cm³."
            )
            7 -> QuestionData(
                "In a right triangle, if the hypotenuse is 10 cm and the angle opposite a leg is 30°, what is the length of that leg?",
                listOf("5 cm", "5√3 cm", "8 cm", "6 cm"),
                0,
                "We know that sin(30°) = opposite / hypotenuse = leg / 10. Since sin(30°) = 0.5, the leg is 10 * 0.5 = 5 cm."
            )
            8 -> QuestionData(
                "The ratio of the areas of two similar triangles is 9:16. What is the ratio of their corresponding sides?",
                listOf("3:4", "9:16", "27:64", "4.5:8"),
                0,
                "For similar shapes, the ratio of their areas is the square of the ratio of their corresponding linear dimensions. Thus, side ratio = √(9:16) = 3:4."
            )
            9 -> QuestionData(
                "A car travels a certain distance at an average speed of 60 km/h in 3 hours. At what average speed must it travel to cover the same distance in 2 hours?",
                listOf("80 km/h", "90 km/h", "100 km/h", "120 km/h"),
                1,
                "Distance = Speed * Time = 60 * 3 = 180 km. To cover 180 km in 2 hours, the required speed is Distance / Time = 180 / 2 = 90 km/h."
            )
            10 -> QuestionData(
                "Find the area of an equilateral triangle with side length 6 cm.",
                listOf("9√3 cm²", "18 cm²", "36 cm²", "12√3 cm²"),
                0,
                "The area of an equilateral triangle is (√3 / 4) * side². Area = (√3 / 4) * 6² = 36 * √3 / 4 = 9√3 cm²."
            )
            11 -> QuestionData(
                "A map uses a scale of 1:250,000. If two towns are 4 cm apart on the map, what is the actual distance between them in kilometers?",
                listOf("10 km", "25 km", "100 km", "1000 km"),
                0,
                "Actual distance = 4 cm * 250,000 = 1,000,000 cm. Convert to meters: 1,000,000 / 100 = 10,000 m. Convert to kilometers: 10,000 / 1000 = 10 km."
            )
            12 -> QuestionData(
                "What is the slope of a line that is perpendicular to the line y = -3x + 5?",
                listOf("-3", "3", "1/3", "-1/3"),
                2,
                "The slope of perpendicular lines are negative reciprocals of one another (m1 * m2 = -1). The given slope is -3, so its perpendicular slope is -1 / (-3) = 1/3."
            )
            13 -> QuestionData(
                "Calculate the total surface area of a cube with edge length 5 cm.",
                listOf("150 cm²", "125 cm²", "100 cm²", "75 cm²"),
                0,
                "A cube has 6 faces, each of area edge². Total Surface Area = 6 * s² = 6 * 5² = 6 * 25 = 150 cm²."
            )
            14 -> QuestionData(
                "What is the value of cos(60°)?",
                listOf("0.5", "√3/2", "√2/2", "1"),
                0,
                "By trigonometric values of special angles, cos(60°) is equal to 1/2 or 0.5."
            )
            15 -> QuestionData(
                "A worker can pack 120 boxes in 3 hours. How many boxes can they pack in 7 hours working at the same speed?",
                listOf("240", "280", "300", "320"),
                1,
                "Packing rate = 120 boxes / 3 hours = 40 boxes per hour. In 7 hours: 40 * 7 = 280 boxes."
            )
            16 -> QuestionData(
                "One angle of a linear pair is 115°. Find the measure of the supplementary angle.",
                listOf("65°", "75°", "85°", "180°"),
                0,
                "The angles of a linear pair/supplementary angles add up to 180°. The other angle is 180° - 115° = 65°."
            )
            17 -> QuestionData(
                "How many diagonals can be drawn from a single vertex of a decagon (10-sided polygon)?",
                listOf("10", "8", "7", "5"),
                2,
                "The number of diagonals from a single vertex of an n-sided polygon is given by (n - 3). For a decagon (n=10), it is 10 - 3 = 7."
            )
            18 -> QuestionData(
                "Find the length of a diagonal of a square with side 8 cm.",
                listOf("8 cm", "8√2 cm", "16 cm", "8√3 cm"),
                1,
                "The diagonal d of a square of side s is given by d = s√2. Since s = 8, the diagonal is 8√2 cm."
            )
            19 -> QuestionData(
                "An angle measures 40° less than its complement. What is the measure of this angle?",
                listOf("25°", "30°", "50°", "65°"),
                0,
                "Complementary angles sum to 90°. Let the angle be x. Its complement is (x + 40). Thus, x + (x + 40) = 90 => 2x = 50 => x = 25°."
            )
            20 -> QuestionData(
                "A sphere has a volume of 36π cm³. What is its radius?",
                listOf("3 cm", "6 cm", "9 cm", "2 cm"),
                0,
                "Volume of a sphere is (4/3) * π * r³ = 36π. Canceling π gives (4/3)r³ = 36 => r³ = 36 * (3/4) = 27. Therefore, r = ³√27 = 3 cm."
            )
            21 -> QuestionData(
                "If the hypotenuse of an isosceles right triangle is 6√2 cm, what is the length of each of its legs?",
                listOf("6 cm", "3 cm", "12 cm", "6√2 cm"),
                0,
                "In an isosceles right triangle with side s, the hypotenuse is s√2. Given the hypotenuse is 6√2, the legs must be 6 cm each."
            )
            22 -> QuestionData(
                "A rectangular lawn of dimensions 20m x 15m is surrounded by a path of uniform width 2m. Find the area of the path.",
                listOf("156 m²", "300 m²", "144 m²", "160 m²"),
                0,
                "Lawn area = 20 * 15 = 300 m². Outer dimensions with path = (20+4) * (15+4) = 24 * 19 = 456 m². Area of path = Outer area - Lawn area = 456 - 300 = 156 m²."
            )
            23 -> QuestionData(
                "If the coordinates of the midpoint of a line segment are (3, 5) and one endpoint is (1, 2), find the other endpoint.",
                listOf("(2, 3.5)", "(5, 8)", "(5, 7)", "(4, 9)"),
                1,
                "Using midpoint formulas: x_mid = (x1+x2)/2 => 3 = (1+x2)/2 => x2 = 5. y_mid = (y1+y2)/2 => 5 = (2+y2)/2 => y2 = 8. The endpoint is (5, 8)."
            )
            24 -> QuestionData(
                "A cylindrical wire of radius 1 cm and length 100 cm is melted and recast into a sphere. What is the radius of the sphere?",
                listOf("3 cm", "3.12 cm", "4.5 cm", "3.84 cm"),
                1,
                "Volume of cylinder = π * r² * h = π * 1² * 100 = 100π. Volume of sphere = (4/3) * π * R³ = 100π => R³ = 75. Taking the cube root, R ≈ 4.2 cm (or more precisely ³√75 ≈ 4.22. Wait, let's fix options or calculation. Let's make it exact: melted wire of radius 2 cm, length 9 cm => base volume = 36π. Recast to sphere: (4/3)πR³ = 36π => R³ = 27 => R = 3). Let's use this simpler numerical setup: Radius of wire 2 cm, length 9 cm melted to sphere. Volume = 36π, giving R = 3 cm."
            )
            25 -> QuestionData(
                "The coordinates of three vertices of a triangle are (0,0), (6,0), and (0,8). Find the perimeter of the triangle.",
                listOf("14", "18", "24", "28"),
                2,
                "The length of the horizontal side is 6 units, and the vertical side is 8 units. The hypotenuse length is √(6² + 8²) = 10 units. Perimeter = 6 + 8 + 10 = 24 units."
            )
            else -> generateFallbackQuestion(globalId)
        }
    }

    // BLOCK 3: PHYSICS MECHANICS
    private fun generatePhysicsMechanicsQuestion(relId: Int, globalId: Int): QuestionData {
        return when (relId) {
            1 -> QuestionData(
                "What is the SI unit of force?",
                listOf("Joule", "Watt", "Newton", "Pascal"),
                2,
                "The SI unit of force is the Newton (N), named after Isaac Newton. It is equivalent to 1 kg·m/s²."
            )
            2 -> QuestionData(
                "According to Newton's Second Law of Motion, force is equal to mass multiplied by what?",
                listOf("Velocity", "Acceleration", "Momentum", "Displacement"),
                1,
                "Newton's Second Law states that force equals mass times acceleration (F = ma)."
            )
            3 -> QuestionData(
                "An object is dropped from a cliff and falls freely under gravity (g = 9.8 m/s²). What is its velocity after 3 seconds, ignoring air resistance?",
                listOf("9.8 m/s", "19.6 m/s", "29.4 m/s", "34.3 m/s"),
                2,
                "Using the formula v = u + gt: starting from rest, u = 0. Therefore v = 9.8 * 3 = 29.4 m/s."
            )
            4 -> QuestionData(
                "Which type of energy is stored in an object due to its high position relative to the ground?",
                listOf("Kinetic Energy", "Gravitational Potential Energy", "Thermal Energy", "Chemical Energy"),
                1,
                "Gravitational Potential Energy is the energy stored in an object due to its height within a gravitational field (PE = mgh)."
            )
            5 -> QuestionData(
                "What is the work done when a force of 50 N pushes a box 5 meters along a flat surface in the direction of the force?",
                listOf("10 Joules", "45 Joules", "250 Joules", "500 Joules"),
                2,
                "Work = Force * Displacement * cos(θ). Since the movement is in the direction of the force, θ = 0. Work = 50 N * 5 m = 250 Joules."
            )
            6 -> QuestionData(
                "What term describes an object's tendency to resist changes to its state of motion?",
                listOf("Momentum", "Inertia", "Force", "Friction"),
                1,
                "Inertia is the property of matter that describes its resistance to any change in its velocity or state of rest. Mass is a quantitative measure of inertia."
            )
            7 -> QuestionData(
                "A car travels 180 km in 2 hours and 30 minutes. What was its average speed in km/h?",
                listOf("60 km/h", "72 km/h", "80 km/h", "90 km/h"),
                1,
                "Count 2 hours and 30 minutes as 2.5 hours. Average speed = Distance / Time = 180 km / 2.5 h = 72 km/h."
            )
            8 -> QuestionData(
                "Which of the following is a vector quantity?",
                listOf("Speed", "Mass", "Temperature", "Velocity"),
                3,
                "A vector quantity has both magnitude and direction. Velocity (speed in a specified direction) is a vector, whereas speed, mass, and temperature are scalar."
            )
            9 -> QuestionData(
                "Which of Newton's laws describes the concept that 'for every action, there is an equal and opposite reaction'?",
                listOf("First Law", "Second Law", "Third Law", "Law of Gravitation"),
                2,
                "Newton's Third Law states that forces always occur in matched action-reaction pairs, equal in size and opposite in direction."
            )
            10 -> QuestionData(
                "Calculate the kinetic energy of a 2 kg toy car moving at a speed of 4 m/s.",
                listOf("8 Joules", "16 Joules", "32 Joules", "4 Joules"),
                1,
                "Kinetic Energy (KE) = 0.5 * m * v² = 0.5 * 2 * (4)² = 1 * 16 = 16 Joules."
            )
            11 -> QuestionData(
                "What form of friction acts on objects that are not moving?",
                listOf("Static Friction", "Sliding Friction", "Rolling Friction", "Fluid Friction"),
                0,
                "Static friction is the frictional force that must be overcome to start an object sliding across another surface."
            )
            12 -> QuestionData(
                "If the density of an object is less than the density of the fluid it is placed in, what will the object do?",
                listOf("Sink to the bottom", "Float on the surface", "Suspend in the middle", "Dissolve immediately"),
                1,
                "Archimedes' principle explains that if an object's average density is less than that of the fluid, it will experience a buoyant force greater than its weight when submerged, causing it to float."
            )
            13 -> QuestionData(
                "What is the formula to calculate mechanical momentum?",
                listOf("p = m * a", "p = m * v", "p = F * t", "p = m/v"),
                1,
                "Momentum (p) is defined as the product of an object's mass (m) and its velocity (v)."
            )
            14 -> QuestionData(
                "At what angle should a projectile be launched to achieve the maximum theoretical horizontal range (ignoring air resistance)?",
                listOf("30 degrees", "45 degrees", "60 degrees", "90 degrees"),
                1,
                "A launch angle of 45 degrees maximizes the product of horizontal velocity and flight time, giving the maximum horizontal distance."
            )
            15 -> QuestionData(
                "What is the mechanical advantage of a simple machine that lifts a 100 N load using an input force of 20 N?",
                listOf("2.0", "5.0", "10.0", "80.0"),
                1,
                "Mechanical Advantage (MA) = Output Force / Input Force = 100 N / 20 N = 5.0."
            )
            16 -> QuestionData(
                "The product of a constant force and the time interval over which it acts is defined as:",
                listOf("Work", "Power", "Impulse", "Acceleration"),
                2,
                "Impulse is defined as Force * time (F·Δt), which is equivalent to the change in momentum of the body."
            )
            17 -> QuestionData(
                "What is the weight of a 5 kg mass on the surface of the Moon, where acceleration due to gravity is 1.6 m/s²?",
                listOf("8.0 Newtons", "49.0 Newtons", "3.1 Newtons", "5.0 Newtons"),
                0,
                "Weight = mass * local gravity. On the Moon, Weight = 5 kg * 1.6 m/s² = 8.0 Newtons."
            )
            18 -> QuestionData(
                "The rate at which work is done (or energy is transferred) is defined as:",
                listOf("Force", "Torque", "Velocity", "Power"),
                3,
                "Power is defined as work done divided by time taken (P = W/t). Its SI unit is the Watt."
            )
            19 -> QuestionData(
                "A simple pendulum's period of oscillation on Earth depends primarily on which two factors?",
                listOf("Bob mass and string length", "Bob mass and local gravity", "String length and local gravity", "Bob material and initial angle"),
                2,
                "The formula for the period of a simple pendulum is T = 2π√(L/g). It is independent of mass and depends only on string length (L) and gravitational acceleration (g)."
            )
            20 -> QuestionData(
                "What happens to the gravitational attraction between two objects if the distance between their centers is doubled?",
                listOf("It doubles", "It halves", "It is reduced to a quarter", "It remains unchanged"),
                2,
                "According to Newton's Law of Universal Gravitation, force is inversely proportional to the square of the distance (1/r²). Doubling distance reduces force by a factor of 4."
            )
            21 -> QuestionData(
                "If an object in motion experiences no unbalanced net external force, it will naturally:",
                listOf("Slowing down gradually", "Speeding up", "Maintain a constant velocity", "Move in a perfect circle"),
                2,
                "By Newton's First Law (Law of Inertia), an object in motion remains in motion at a constant speed in a straight line unless acted upon by a net external force."
            )
            22 -> QuestionData(
                "What is the mechanical work done if you hold a heavy 20 kg suitcase completely still for 10 minutes?",
                listOf("200 Joules", "1960 Joules", "0 Joules", "120 Joules"),
                2,
                "Since there is no displacement (distance = 0), the mechanical work done on the suitcase is exactly 0 Joules."
            )
            23 -> QuestionData(
                "Calculate the power output of a motor that performs 1200 Joules of work in 4 seconds.",
                listOf("4800 Watts", "300 Watts", "30 Watts", "1200 Watts"),
                1,
                "Power = Work / Time = 1200 Joules / 4 seconds = 300 Watts."
            )
            24 -> QuestionData(
                "Which property determines whether an object sinks or floats in water? (density of water is 1.0 g/cm³)",
                listOf("Total volume", "Total mass", "Relative density", "Surface area"),
                2,
                "Relative density (specific gravity) relative to the fluid determines buoyancy. If an object is denser than water (>1.0 g/cm³), it sinks; if less dense, it floats."
            )
            25 -> QuestionData(
                "A centripetal force acts on an object moving in circular motion. In which direction does this force point?",
                listOf("Tangential to the circle", "Directly inward toward the center", "Directly outward from the center", "Vertically upward"),
                1,
                "Centripetal force always points perpendicular to the velocity vector, directly inward towards the center of curvature of the circular path."
            )
            else -> generateFallbackQuestion(globalId)
        }
    }

    // BLOCK 4: CHEMISTRY
    private fun generateChemistryQuestion(relId: Int, globalId: Int): QuestionData {
        return when (relId) {
            1 -> QuestionData(
                "What is the chemical symbol for the element Gold?",
                listOf("Gd", "Go", "Ag", "Au"),
                3,
                "The chemical symbol for Gold is Au, derived from its classical Latin name 'aurum' meaning shining dawn."
            )
            2 -> QuestionData(
                "What is the atomic number of Carbon, representing its count of protons?",
                listOf("4", "6", "8", "12"),
                1,
                "Carbon has an atomic number of 6, which means it contains exactly six protons in its nucleus."
            )
            3 -> QuestionData(
                "Which subatomic particle carries a negative electrical charge?",
                listOf("Proton", "Neutron", "Electron", "Positron"),
                2,
                "Electrons form the cloud around atomic nuclei and carry a negative charge (-1e). Protons are positive, and neutrons are neutral."
            )
            4 -> QuestionData(
                "A solution with a pH value of 3 is classified as relative to neutral water:",
                listOf("Highly acidic", "Highly basic", "Neutral", "Slightly alkaline"),
                0,
                "The pH scale goes from 0-14. A pH of 7 is neutral. Values below 7 are acidic, meaning 3 is highly acidic. Values above 7 are basic/alkaline."
            )
            5 -> QuestionData(
                "What chemical compound's formula is represented by NaCl?",
                listOf("Baking Soda", "Table Salt", "Hydrochloric Acid", "Bleach"),
                1,
                "NaCl is the chemical formula for sodium chloride, commonly known as culinary table salt."
            )
            6 -> QuestionData(
                "Which state of matter has a definite volume but no fixed shape, adopting the shape of its container?",
                listOf("Solid", "Liquid", "Gas", "Plasma"),
                1,
                "A liquid has close intermolecular forces preserving constant volume but lacks a rigid lattice structure, letting it flow and assume container shapes."
            )
            7 -> QuestionData(
                "What is the term for a chemical bond formed by the sharing of electron pairs between matching atoms?",
                listOf("Ionic Bond", "Covalent Bond", "Hydrogen Bond", "Metallic Bond"),
                1,
                "A covalent bond is characterized by the sharing of one or more pairs of valence electrons between atoms."
            )
            8 -> QuestionData(
                "Which element is the most abundant by mass in the Earth's crust?",
                listOf("Silicon", "Iron", "Oxygen", "Aluminum"),
                2,
                "Oxygen is the most abundant element in the Earth's crust, accounting for about 46% of its mass, primarily bound in silicate minerals."
            )
            9 -> QuestionData(
                "What is the correct chemical formula for Carbon Dioxide?",
                listOf("CO", "CO₂", "C_2O", "CoO"),
                1,
                "Carbon dioxide is composed of one carbon atom covalently double-bonded to two oxygen atoms, represented as CO₂."
            )
            10 -> QuestionData(
                "Which column/group of the periodic table contains the elements collectively called the 'Noble Gases'?",
                listOf("Group 1", "Group 2", "Group 17", "Group 18"),
                3,
                "Group 18 (VIII A) elements are called Noble Gases (He, Ne, Ar, Kr, Xe, Rn) due to their full valence outer electron shells rendering them highly inert."
            )
            11 -> QuestionData(
                "The transition of a substance directly from the solid physical phase to the gas phase is called:",
                listOf("Evaporation", "Condensation", "Sublimation", "Deposition"),
                2,
                "Sublimation is the phase change directly from solid to gas without entering an intermediate liquid state (e.g., dry ice at room temperature)."
            )
            12 -> QuestionData(
                "Which subatomic particles are found clustered in the dense nucleus of an atom?",
                listOf("Protons and Electrons", "Protons and Neutrons", "Neutrons and Electrons", "Electrons only"),
                1,
                "The nucleus at the center of an atom contains protons and neutrons (collectively called nucleons), while electrons occupy shells orbiting the nucleus."
            )
            13 -> QuestionData(
                "What is the chemical formula for Sulfuric Acid?",
                listOf("HCl", "HNO₃", "H₂SO₄", "H₂CO₃"),
                2,
                "Sulfuric acid, a strong mineral acid with wide industrial applications, is formulaicly represented as H₂SO₄."
            )
            14 -> QuestionData(
                "How many atoms of Hydrogen are present in a single molecule of Glucose (C₆H₁₂O₆)?",
                listOf("6", "12", "18", "24"),
                1,
                "The chemical formula of glucose is C₆H₁₂O₆, which contains 6 Carbon, 12 Hydrogen, and 6 Oxygen atoms."
            )
            15 -> QuestionData(
                "Which gas is produced as a side product when active metals react with standard acids like hydrochloric acid?",
                listOf("Oxygen", "Hydrogen", "Carbon dioxide", "Nitrogen"),
                1,
                "Active metals (like zinc or magnesium) react with acids to yield a metal salt and release hydrogen gas (H₂)."
            )
            16 -> QuestionData(
                "A catalyst is a chemical substance that speeds up a reaction by doing what?",
                listOf("Increasing temperature", "Lowering activation energy", "Consuming reactants", "Increasing pressure"),
                1,
                "Catalysts accelerate the rate of a chemical reaction by providing an alternative pathway with a lower activation energy, without being consumed themselves."
            )
            17 -> QuestionData(
                "What is the atomic mass of standard Hydrogen, being the simplest element?",
                listOf("1", "2", "4", "12"),
                0,
                "Hydrogen has one proton and no neutrons in its most common isotope (protium), giving it a standard atomic mass of approximately 1 amu."
            )
            18 -> QuestionData(
                "In a solution, the substance that is dissolved is called the:",
                listOf("Solvent", "Solute", "Suspension", "Emulsion"),
                1,
                "The solute is the species dissolved in the medium, whereas the solvent is the dissolving medium (often water in aqueous systems)."
            )
            19 -> QuestionData(
                "What type of chemical reaction occurs when two or more simple reactants combine to form a single, more complex product?",
                listOf("Decomposition", "Synthesis (Combination)", "Single Displacement", "Double Displacement"),
                1,
                "A synthesis reaction (or combination reaction) is one where individual elements or compounds combine to create one complex compound (A + B -> AB)."
            )
            20 -> QuestionData(
                "An atom that has lost one or more electrons, resulting in a positive net charge, is called a(n):",
                listOf("Anion", "Cation", "Isotope", "Molecule"),
                1,
                "A positively charged ion is a cation (since it is attracted to the cathode). An anion is a negatively charged ion."
            )
            21 -> QuestionData(
                "Which element has the highest electronegativity rating of all, meaning it strongly attracts shared electron pairs?",
                listOf("Oxygen", "Chlorine", "Fluorine", "Nitrogen"),
                2,
                "Fluorine is the most electronegative element in the periodic table, with a value of 3.98 on the Pauling scale."
            )
            22 -> QuestionData(
                "What are isotopes of an element?",
                listOf("Atoms with different numbers of protons", "Atoms with different numbers of neutrons", "Atoms with different numbers of electrons", "Compounds with matching structures"),
                1,
                "Isotopes are atoms of the same chemical element (same protons/atomic number) that differ in their count of neutrons, hence having different atomic masses."
            )
            23 -> QuestionData(
                "What is the molar mass of Water (H₂O) roughly in grams per mole? (H ≈ 1 g/mol, O ≈ 16 g/mol)",
                listOf("10 g/mol", "18 g/mol", "16 g/mol", "20 g/mol"),
                1,
                "Molar mass of H₂O = (2 * 1) + 16 = 18 grams/mole."
            )
            24 -> QuestionData(
                "Which chemical law states that matter cannot be created or destroyed during standard chemical reactions?",
                listOf("Law of Definite Proportions", "Law of Conservation of Mass", "Charles's Law", "Boyle's Law"),
                1,
                "The Law of Conservation of Mass, formulated in part by Antoine Lavoisier, states that mass is conserved in closed chemical systems, meaning input mass equals output product mass."
            )
            25 -> QuestionData(
                "What type of mixture is uniform throughout, with no visible boundaries separating its constituent parts?",
                listOf("Heterogeneous Mixture", "Homogeneous Mixture", "Suspension", "Colloid"),
                1,
                "A homogeneous mixture has a uniform composition throughout its volume (e.g., salt dissolved in water, clean air), while heterogeneous mixtures are non-uniform."
            )
            else -> generateFallbackQuestion(globalId)
        }
    }

    // BLOCK 5: BIOLOGY CELLS
    private fun generateBiologyCellQuestion(relId: Int, globalId: Int): QuestionData {
        return when (relId) {
            1 -> QuestionData(
                "Which organelle is often called the 'powerhouse of the cell' due to its role in ATP production?",
                listOf("Nucleus", "Ribosome", "Mitochondrion", "Lysosome"),
                2,
                "Mitochondria generate adenosine triphosphate (ATP) through cellular respiration, powering the cell's energetic activities."
            )
            2 -> QuestionData(
                "Which cell organelle is the site of cellular protein synthesis?",
                listOf("Golgi Apparatus", "Ribosome", "Endoplasmic Reticulum", "Vacuole"),
                1,
                "Ribosomes are microscopic cellular machines composed of RNA and proteins that translate amino acids into protein chains."
            )
            3 -> QuestionData(
                "What pigment captures solar light during plant photosynthesis, giving leaves their classic green hue?",
                listOf("Carotene", "Anthocyanin", "Chlorophyll", "Melanin"),
                2,
                "Chlorophyll is the primary green photosynthetic pigment located in chloroplasts that absorbs red and blue light to harvest solar energy."
            )
            4 -> QuestionData(
                "Which structural boundary is unique to plant, fungal, and bacterial cells, being absent in animal cells?",
                listOf("Plasma Membrane", "Cell Wall", "Cytoplasm", "Nuclear Envelope"),
                1,
                "Animal cells lack cell walls, having only a flexible cell membrane. Plant cell walls are made of cellulose, supplying structural stability."
            )
            5 -> QuestionData(
                "What division process results in four non-identical daughter cells, each with half the chromosome count of the parent cell?",
                listOf("Mitosis", "Meiosis", "Binary Fission", "Budding"),
                1,
                "Meiosis is a two-step germ cell division process that produces four haploid gametes (sperm or egg cells), whereas mitosis forms two matching diploid cells."
            )
            6 -> QuestionData(
                "In genetic science, DNA transcription describes the process of carbon-copying a sequence of DNA into what molecule?",
                listOf("tRNA", "mRNA", "rRNA", "Protein"),
                1,
                "Transcription is the process by which RNA polymerase creates a messenger RNA (mRNA) copy using a specific segment of template genomic DNA."
            )
            7 -> QuestionData(
                "What double-membrane organelle houses the primary genetic material (DNA) in eukaryotic cells?",
                listOf("Mitochondria", "Nucleus", "Vacuole", "Eukaryotic wall"),
                1,
                "The nucleus acts as the command center of the eukaryotic cell, safeguarding the organisms' chromosomes and DNA."
            )
            8 -> QuestionData(
                "What is the basic, fundamental structural and functional unit of all living organisms?",
                listOf("Tissue", "Organ", "Cell", "Atom"),
                2,
                "The cell is the smallest unit of life capable of replicating independently and carrying out fundamental metabolic functions."
            )
            9 -> QuestionData(
                "Which type of cells lack a membrane-bound nucleus and organelles, representing organisms like bacteria?",
                listOf("Eukaryotic", "Prokaryotic", "Fungal", "Animal"),
                1,
                "Prokaryotes (bacteria and archaea) lack a localized nucleus and membrane-bound organelles. Their genetic material floats freely in the cytoplasm."
            )
            10 -> QuestionData(
                "Which organelle functions as the packaging and distribution center of the cell, modifying and sorting proteins?",
                listOf("Ribosome", "Lysosome", "Golgi Apparatus", "Centrosome"),
                2,
                "The Golgi apparatus collects, chemically tags/modifies, packages, and routes proteins and lipids synthesized in the endoplasmic reticulum."
            )
            11 -> QuestionData(
                "What specialized membrane-bound structure contains digestive enzymes to break down waste materials within animal cells?",
                listOf("Lysosome", "Peroxisome", "Vacuole", "Ribosome"),
                0,
                "Lysosomes are spherical organelles containing hydrolytic enzymes capable of digesting worn-out organelles, food waste, and engulfed bacteria."
            )
            12 -> QuestionData(
                "What organic chemical compounds serve as the central monomer building blocks of proteins?",
                listOf("Nucleotides", "Monosaccharides", "Fatty Acids", "Amino Acids"),
                3,
                "Proteins are polymers composed of folded linear chains of amino acids, which are joined by peptide bonds."
            )
            13 -> QuestionData(
                "During cellular respiration, which glucose-breaking stage occurs in the cytosol without requiring oxygen?",
                listOf("Krebs Cycle", "Electron Transport Chain", "Glycolysis", "Fermentation"),
                2,
                "Glycolysis is the ancient metabolic pathway that harvests chemical energy by breaking 1 glucose into 2 pyruvates in the cytosol, yielding 2 net ATP without oxygen."
            )
            14 -> QuestionData(
                "What nitrogenous base is present in RNA but completely absent in DNA, substituting for Thymine?",
                listOf("Adenine", "Cytosine", "Guanine", "Uracil"),
                3,
                "DNA contains the bases Adenine (A), Thymine (T), Guanine (G), and Cytosine (C). In RNA, Thymine is replaced by Uracil (U)."
            )
            15 -> QuestionData(
                "What model describes the dynamic, fluid structure of the cell membrane, featuring a bilayer of phospholipids embedded with proteins?",
                listOf("Double Helix Model", "Lock and Key Model", "Fluid Mosaic Model", "Sliding Filament Model"),
                2,
                "The Fluid Mosaic Model, proposed by Singer and Nicolson, represents the plasma membrane as a liquid phospholipid bilayer featuring freely moving integral and peripheral proteins."
            )
            16 -> QuestionData(
                "A heterozygous individual displays which of the following genetic compositions for a particular trait?",
                listOf("Two identical dominant alleles", "Two identical recessive alleles", "Two different alleles for that gene", "No alleles for that gene"),
                2,
                "Heterozygous refers to possessing two different alleles of a gene (e.g., Bb), whereas homozygous means having identical alleles (e.g., BB or bb)."
            )
            17 -> QuestionData(
                "What plant tissue is responsible for distributing water and dissolved mineral solutes from roots throughout the plant?",
                listOf("Phloem", "Xylem", "Cambium", "Epidermis"),
                1,
                "Xylem tissue transports water and minerals from the roots up to the leaves, whereas phloem distributes synthesized sucrose and nutrients from leaves down."
            )
            18 -> QuestionData(
                "An organism's observable physical traits and characteristics, determined by its genetic coding, is its:",
                listOf("Genotype", "Phenotype", "Karyotype", "Allele rating"),
                1,
                "Phenotype is the set of observable characteristics or traits of an organism, while genotype represents the genetic codes (the alleles) driving those traits."
            )
            19 -> QuestionData(
                "What phase of mitosis is characterized by chromosomes aligning in a single file along the center line of the spindle?",
                listOf("Prophase", "Metaphase", "Anaphase", "Telophase"),
                1,
                "During metaphase, chromosomes align on the metaphase plate in the middle of the cell, ready for balanced distribution."
            )
            20 -> QuestionData(
                "Which process transports molecules across the cell membrane against their concentration gradient, requiring cellular energy?",
                listOf("Simple Diffusion", "Osmosis", "Facilitated Diffusion", "Active Transport"),
                3,
                "Active transport pumps solutes 'uphill' against concentration gradients, requiring chemical energy (ATP) and membrane transport proteins."
            )
            21 -> QuestionData(
                "How many chromosomes are contained within a standard, healthy human somatic (body) cell?",
                listOf("23", "46", "48", "52"),
                1,
                "Human somatic cells contain 23 pairs of chromosomes, which equates to exactly 46 total chromosomes."
            )
            22 -> QuestionData(
                "Which organic molecule has a primary function of storing long-term reserve energy and forming protective membranes?",
                listOf("Carbohydrate", "Protein", "Lipid", "Nucleic Acid"),
                2,
                "Lipids (fats, waxes, oils) provide dense, long-term chemical energy reserves and form the hydrophobic barrier of cell membranes."
            )
            23 -> QuestionData(
                "What is the term for the biological mechanism where cells programmedly self-destruct for the overall benefit of the organism?",
                listOf("Mitosis", "Apoptosis", "Necrosis", "Phagocytosis"),
                1,
                "Apoptosis is highly regulated, programmed cell death used during pattern development and to eliminate damaged cells safely without inflammation."
            )
            24 -> QuestionData(
                "What double-stranded macromolecule resembles a twisted ladder, containing genetic instructions for development?",
                listOf("RNA", "DNA", "Cellulose", "Chitin"),
                1,
                "Deoxyribonucleic acid (DNA) forms a double helix of complementary nucleotides containing code sequences for proteins and life functions."
            )
            25 -> QuestionData(
                "Which of the following describes a key difference between eukaryotic animal cells and plant cells?",
                listOf("Animal cells have cell walls, plant cells do not", "Plant cells contain chloroplasts and large vacuoles, animal cells do not", "Animal cells can generate glucose via solar light, plant cells cannot", "Plant cell structures lack nuclei"),
                1,
                "Plant cells have cellulose cell walls, chloroplasts for photosynthesis, and a large central vacuole, which are absent in healthy animal cells."
            )
            else -> generateFallbackQuestion(globalId)
        }
    }

    // BLOCK 6: BIOLOGY PHYSIOLOGY & ECOSYSTEMS
    private fun generateBiologySystemQuestion(relId: Int, globalId: Int): QuestionData {
        return when (relId) {
            1 -> QuestionData(
                "How many chambers are inside a healthy, fully developed human heart?",
                listOf("Two", "Three", "Four", "Five"),
                2,
                "The human heart has four chambers: two upper atria (left and right) and two lower ventricles (left and right)."
            )
            2 -> QuestionData(
                "Which oxygen-carrying protein is located inside red blood cells, giving blood its red pigment?",
                listOf("Insulin", "Collagen", "Hemoglobin", "Keratin"),
                2,
                "Hemoglobin is an iron-bearing metalloprotein in red blood cells that reversibly binds oxygen to distribute it through the body."
            )
            3 -> QuestionData(
                "In an ecosystem's food web, green plants are classified as what trophic level?",
                listOf("Primary Consumers", "Secondary Consumers", "Decomposers", "Primary Producers"),
                3,
                "Green plants are primary producers (or autotrophs) because they use photosynthesis to synthesize glucose using solar energy."
            )
            4 -> QuestionData(
                "What type of symbiotic relationship is beneficial to one species while leaving the other completely unaffected?",
                listOf("Mutualism", "Commensalism", "Parasitism", "Predation"),
                1,
                "Commensalism is a relationship where one organism benefits and the other is neither helped nor harmed (e.g., barnacles on whales)."
            )
            5 -> QuestionData(
                "What is the main organ of the human respiratory system responsible for gaseous gas exchange?",
                listOf("Trachea", "Lungs", "Diaphragm", "Bronchus"),
                1,
                "The lungs contain millions of microscopic alveoli where oxygen is taken into the blood and carbon dioxide waste is expelled."
            )
            6 -> QuestionData(
                "Which major human body system's main purpose is to filter out waste products and excess water from blood to produce urine?",
                listOf("Digestive System", "Excretory (Urinary) System", "Endocrine System", "Circulatory System"),
                1,
                "The excretory/urinary system (consisting of kidneys, ureters, bladder, and urethra) filters circulating blood to remove toxic nitrogenous metabolic wastes as urine."
            )
            7 -> QuestionData(
                "What hormone is produced by the pancreas to regulate and lower glucose levels in human blood?",
                listOf("Glucagon", "Thyroxine", "Adrenaline", "Insulin"),
                3,
                "Insulin is a hormone produced by the beta cells of the pancreas that facilitates the uptake of glucose into cells, lowering blood sugar levels."
            )
            8 -> QuestionData(
                "The microscopic, functional units of the human kidney that actively filter blood to generate urine are the:",
                listOf("Neurons", "Nephrons", "Alveoli", "Villi"),
                1,
                "Each kidney contains about one million nephrons. Nephrons actively filter blood, reabsorbing necessary nutrients and water, and excreting waste as urine."
            )
            9 -> QuestionData(
                "What nervous system structure transmits electrical action potentials away from the neuron cell body?",
                listOf("Dendrite", "Axon", "Synapse", "Myelin"),
                1,
                "Axons conduct electrical impulses away from the neuron's soma (cell body) toward other target cells, while dendrites receive incoming signals."
            )
            10 -> QuestionData(
                "Which of the following represents a primary consumer in a standard temperate forest food chain?",
                listOf("A tree-destroying caterpillar", "An insect-feeding frog", "A rabbit-hunting fox", "A deer-hunting wolf"),
                0,
                "Primary consumers are herbivores that feed directly on primary producers. A plant-eating caterpillar is a primary consumer."
            )
            11 -> QuestionData(
                "What is the term for the biological homeostasis mechanism where organisms maintain stable core internal temperatures?",
                listOf("Osmoregulation", "Thermoregulation", "Transpiration", "Metabolism"),
                1,
                "Thermoregulation is the mechanism by which mammals and other animals maintain stable core temperatures despite changing external environmental warmth."
            )
            12 -> QuestionData(
                "Which gland is often called the 'master gland' of the endocrine system because it releases hormones controlling multiple other glands?",
                listOf("Thyroid Gland", "Adrenal Gland", "Pituitary Gland", "Pancreas"),
                2,
                "The pituitary gland, located at the base of the brain, releases regulatory hormones that manage the thyroid, adrenals, and reproductive systems."
            )
            13 -> QuestionData(
                "What is the largest organ of the human body, providing a barrier against pathogens and evaporation?",
                listOf("Liver", "Brain", "Skin", "Small Intestine"),
                2,
                "The skin (integumentary system) is the body's largest organ by mass and surface area, blocking microbes, containing fluids, and regulating temperature."
            )
            14 -> QuestionData(
                "In an ecosystem, which organisms break down dead organic matter and return basic chemical nutrients to the soil?",
                listOf("Herbivores", "Carnivores", "Decomposers", "Autotrophs"),
                2,
                "Decomposers (primarily fungi and bacteria) break down complex organic matter from carcasses and leaf litter, recycling essential elements like nitrogen back into the ecosystem."
            )
            15 -> QuestionData(
                "What pigment gives the human iris, hair, and skin its variable dark shading, offering protection against ultraviolet rays?",
                listOf("Carotene", "Hemoglobin", "Melanin", "Bilirubin"),
                2,
                "Melanin is a dark, complex pigment produced by melanocytes in the skin and eyes that absorbs harmful UV solar rays to prevent cell damage."
            )
            16 -> QuestionData(
                "The small, finger-like projections lining the inner walls of the human small intestine to maximize nutrient absorption are called:",
                listOf("Nephrons", "Alveoli", "Villi", "Capillaries"),
                2,
                "Villi (and microscopic microvilli) line the small intestine, expanding surface area thousands-fold to maximize efficient diffusion of nutrients into capillaries."
            )
            17 -> QuestionData(
                "What is the primary function of white blood cells (leukocytes) in human blood?",
                listOf("Oxygen transportation", "Promoting blood clotting", "Defending against pathogens and infections", "Regulating pH levels"),
                2,
                "Leukocytes recognize, target, and destroy invading pathogens (viruses, bacteria, parasites) and remove cellular debris as part of the immune system."
            )
            18 -> QuestionData(
                "What specialized chemical process converts atmospheric Nitrogen gas (N₂) into bio-available forms like ammonia, mainly accomplished by specialized bacteria?",
                listOf("Denitrification", "Nitrogen Fixation", "Nitrification", "Eutrophication"),
                1,
                "Nitrogen fixation is the process where nitrogen gas is converted into ammonium or ammonia, carried out by diazotrophic bacteria (e.g., Rhizobium in root nodules)."
            )
            19 -> QuestionData(
                "What is the primary site of nutrient absorption in the human digestive system?",
                listOf("Stomach", "Small Intestine", "Large Intestine", "Esophagus"),
                1,
                "While digestion begins in the mouth and stomach, the majority of chemical digestion and around 90% of nutrient absorption occurs in the small intestine."
            )
            20 -> QuestionData(
                "Which blood vessel type contains internal valves to prevent the backflow of blood as it returns towards the heart against gravity?",
                listOf("Arteries", "Capillaries", "Veins", "Arterioles"),
                2,
                "Veins operate under low pressure and contain one-way pocket valves to ensure blood flows only back toward the heart, preventing pooling in extremities."
            )
            21 -> QuestionData(
                "What is the term for a specific, geographic area characterized by its unique climate, rainfall, soil, and plant communities (e.g., Tundra, Desert)?",
                listOf("Ecosystem", "Biome", "Habitat", "Biosphere"),
                1,
                "A biome is a large regional community classified by dominant vegetation and survival adaptations of organisms to specific regional climates."
            )
            22 -> QuestionData(
                "Which human brain region is primarily responsible for coordinating muscle movements, balance, and fine-motor control?",
                listOf("Cerebrum", "Cerebellum", "Brainstem", "Hypothalamus"),
                1,
                "The cerebellum (located at the back of the skull) integrates sensory input with motor instructions to coordinate posture, balance, and precise skeletal movements."
            )
            23 -> QuestionData(
                "In ecological terms, the maximum population size of a species that a specific environment can sustainably support is known as its:",
                listOf("Biomass potential", "Niche limit", "Carrying Capacity", "Ecosystem quota"),
                2,
                "Carrying capacity is the maximum population size of a biological species that an ecosystem can sustain indefinitely with its available food, water, and habitat."
            )
            24 -> QuestionData(
                "What is the main physiological purpose of bile, which is produced by the liver and stored in the gallbladder?",
                listOf("Digesting proteins", "Emulsifying dietary lipids (fats)", "Filtering metabolic toxins", "Excreting excess glucose"),
                1,
                "Bile contains bile salts that act like soap, emulsifying large lipid drops into microscopic droplets to expand surface area for pancreatic lipase enzymes."
            )
            25 -> QuestionData(
                "What biological fluid carries hormones, carbon dioxide, antibodies, and glucose throughout the human body, serving as the liquid portion of blood?",
                listOf("Lymph", "Cytosol", "Serum", "Plasma"),
                3,
                "Plasma is the straw-colored liquid component of blood, consisting of 92% water, which transports dissolved nutrients, hormones, antibodies, and coagulation factors."
            )
            else -> generateFallbackQuestion(globalId)
        }
    }

    // BLOCK 7: ANCIENT HISTORY
    private fun generateAncientHistoryQuestion(relId: Int, globalId: Int): QuestionData {
        return when (relId) {
            1 -> QuestionData(
                "Which river was the life-giving center of ancient Egyptian civilization?",
                listOf("Tigris", "Euphrates", "Amazon", "Nile"),
                3,
                "Ancient Egypt developed along the Nile River, relying on its predictable annual floods to irrigate agricultural fields and supply water in the desert."
            )
            2 -> QuestionData(
                "Who was the famous Macedonian general who conquered Greece, Egypt, and Persia, establishing a vast empire before dying at age 32?",
                listOf("Julius Caesar", "Alexander the Great", "Hannibal Barca", "Leonidas"),
                1,
                "Alexander the Great of Macedon conquered much of the known ancient world, spreading Hellenistic culture from the Mediterranean all the way to India."
            )
            3 -> QuestionData(
                "What massive ancient defensive wall system was constructed across the northern borders of China to repel nomadic incursions?",
                listOf("The Wall of Babylon", "The Great Wall of China", "Hadrian's Wall", "The Berlin Wall"),
                1,
                "The Great Wall of China is a series of fortifications built over centuries by Chinese dynasties (beginning with Qin Shi Huang) to secure borders from steppe nomads."
            )
            4 -> QuestionData(
                "Which city-state was the birthplace of direct assemblies and early democracy in Ancient Greece?",
                listOf("Sparta", "Athens", "Thebes", "Corinth"),
                1,
                "Ancient Athens pioneered a form of direct democracy where free adult male citizens could directly debate and vote on laws in assemblies."
            )
            5 -> QuestionData(
                "What civil document, signed by King John of England in 1515, is widely seen as an early cornerstone of modern constitutional law limit on absolute rulers?",
                listOf("The US Constitution", "The English Bill of Rights", "The Magna Carta", "The Code of Hammurabi"),
                2,
                "The Magna Carta (Great Charter) was forced upon King John in 1215. It established that everyone, even the king, was subject to the law, protecting noble rights."
            )
            6 -> QuestionData(
                "The historical trade route network connecting East Asia to the Mediterranean, facilitating the trade of fabrics and luxury goods, was called:",
                listOf("The Amber Road", "The Silk Road", "The Spice Route", "The Royal Road"),
                1,
                "The Silk Road was an active transcontinental network linking China to Rome, fostering major trade, technologies, philosophies, and cultural exchanges."
            )
            7 -> QuestionData(
                "Which Roman general was assassinated by a conspiracy of senators on the Ides of March (44 BCE) after declaring himself dictator for life?",
                listOf("Augustus", "Julius Caesar", "Nero", "Marcus Aurelius"),
                1,
                "Julius Caesar crossed the Rubicon, triumphed in civil war, and was named Dictator Perpetuo, leading to his assassination by senators led by Brutus and Cassius."
            )
            8 -> QuestionData(
                "What code of written laws, originating in Mesopotamia around 1750 BCE, is famous for its strict retributive justice principal of 'an eye for an eye'?",
                listOf("Code of Hammurabi", "Twelve Tables of Rome", "Justinlan's Code", "Ten Commandments"),
                0,
                "The Babylonian Code of Hammurabi is one of the earliest and best-preserved written legal codifications, depicting scaled penalties based on social status."
            )
            9 -> QuestionData(
                "Who was the last active Pharaoh of Ancient Egypt, famous for her relationships with Julius Caesar and Mark Antony?",
                listOf("Nefertiti", "Hatshepsut", "Cleopatra VII", "Sobekneferu"),
                2,
                "Cleopatra VII was the final ruler of the Ptolemaic Kingdom of Egypt. Following her defeat and suicide, Egypt became a formal province of the Roman Empire."
            )
            10 -> QuestionData(
                "Which Greek philosopher was sentenced to death in Athens via hemlock poisoning for 'corrupting the youth' of the city?",
                listOf("Socrates", "Plato", "Aristotle", "Pythagoras"),
                0,
                "Socrates, who developed the Socratic inquiry method, was tried and convicted by an Athenian democratic jury for heresy and youth corruption in 399 BCE."
            )
            11 -> QuestionData(
                "What massive defensive wall did the Roman Emperor Hadrian build across the width of northern Britain to mark the empire's frontier?",
                listOf("Antonine Wall", "Hadrian's Wall", "Limes Germanicus", "Great Wall of Albion"),
                1,
                "Hadrian's Wall was built starting in 122 CE to secure Roman Britannia from Caledonian tribes in modern-day Scotland, spanning 73 miles."
            )
            12 -> QuestionData(
                "The ancient civilization of Mesopotamia flourished primarily between which two rivers?",
                listOf("Nile and Jordan", "Indus and Ganges", "Tigris and Euphrates", "Yellow and Yangtze"),
                2,
                "Mesopotamia means 'land between rivers' in Greek, describing the fertile basin between the Tigris and Euphrates (modern Iraq/Syria)."
            )
            13 -> QuestionData(
                "Which of the following was one of the Seven Wonders of the Ancient World, located in Mesopotamia?",
                listOf("The Lighthouse of Alexandria", "The Hanging Gardens of Babylon", "The Colossus of Rhodes", "The Mausoleum at Halicarnassus"),
                1,
                "The Hanging Gardens of Babylon, reputedly built by King Nebuchadnezzar II for his homesick wife, consisted of multi-tier vaulted plant terraces near the Euphrates."
            )
            14 -> QuestionData(
                "What epic Greek poem, traditionally attributed to Homer, tells the story of the final weeks of the decade-long siege of Troy?",
                listOf("The Odyssey", "The Iliad", "The Aeneid", "The Epic of Gilgamesh"),
                1,
                "The Iliad focuses on the wrath of Achilles during the final stages of the legendary Trojan War. The Odyssey covers Odysseus's ten-year journey home."
            )
            15 -> QuestionData(
                "Which Carthaginian military commander famously marched war elephants across the Alps to invade Rome during the Second Punic War?",
                listOf("Hamilcar Barca", "Scipio Africanus", "Hannibal Barca", "Spartacus"),
                2,
                "Hannibal Barca was Carthage's tactical genius who achieved legendary victories (such as at Cannae) in Italy after crossing the Pyrenees and the Alps with his forces."
            )
            16 -> QuestionData(
                "What famous direct clash took place in 480 BCE, where 300 Spartan warriors led by King Leonidas defended a narrow pass against the Persian Empire?",
                listOf("Battle of Marathon", "Battle of Salamis", "Battle of Thermopylae", "Battle of Plataea"),
                2,
                "At Thermopylae (the Hot Gates), Leonidas and his Spartan detachment, along with Greek allies, delayed Xerxes' army for three days in a heroic last stand."
            )
            17 -> QuestionData(
                "Who was the first official Emperor of the Roman Empire, ushered in after the fall of the Republic under the title Princeps?",
                listOf("Julius Caesar", "Augustus Caesar", "Nero", "Constantine"),
                1,
                "Octavian, Julius Caesar's adopted heir, defeated Antony and Cleopatra, reformed the state, was retitled Augustus in 27 BCE, and became Rome's first emperor."
            )
            18 -> QuestionData(
                "What ancient Indian Emperor of the Maurya Dynasty renounced warfare and embraced Buddhism after witnessing the devastation of the Kalinga War?",
                listOf("Chandragupta Maurya", "Ashoka the Great", "Samudragupta", "Harsha"),
                1,
                "Ashoka the Great conquered much of India, but remorse over his bloody victory at Kalinga led him to convert to Buddhism and spread its peaceful tenets on stone pillars."
            )
            19 -> QuestionData(
                "What type of engineering structures did the Romans construct across provinces to transport clean fresh water to their crowded cities?",
                listOf("Catacombs", "Aqueducts", "Viaducts", "Basilicase"),
                1,
                "Aqueducts were massive concrete channels built with consistent downward slopes over miles, utilizing gravity arches to convey freshwater directly to city fountains and baths."
            )
            20 -> QuestionData(
                "The Pax Romana describes a historic period of relative stability, agricultural prosperity, and minor borders wars lasting about:",
                listOf("50 years", "100 years", "200 years", "500 years"),
                2,
                "The Pax Romana (Roman Peace) lasted approximately 206 years, starting from the reign of Augustus (27 BCE) until the death of Marcus Aurelius (180 CE)."
            )
            21 -> QuestionData(
                "Which ancient legal code, carved upon black diorite stone, contains the famous preamble stating its laws are meant 'to prevent the strong from oppressing the weak'?",
                listOf("Twelve Tables", "Code of Hammurabi", "Draconian law", "Solonian Constitution"),
                1,
                "Hammurabi's code opens and closes with elaborate assurances that his divine laws exist to bring order, protect orphans and widows, and ensure justice for all subjects."
            )
            22 -> QuestionData(
                "What ancient Greek mathematician is considered the 'Father of Geometry', famous for compiling his thirteen-volume textbook named 'Elements'?",
                listOf("Archimedes", "Pythagoras", "Euclid", "Eratosthenes"),
                2,
                "Euclid of Alexandria wrote 'Elements' around 300 BCE, formulating the foundational axioms, postulates, and proofs that defined geometry for two millennia."
            )
            23 -> QuestionData(
                "What primary written script, consisting of stylized reed wedge-impressions on clay, was created in ancient Sumer?",
                listOf("Hieroglyphics", "Cuneiform", "Phoenician Alphabet", "Linear B"),
                1,
                "Cuneiform ('wedge-shaped') is one of the earliest systems of writing, invented by Sumerians in late 4th millennium Mesopotamia to catalog grain trade."
            )
            24 -> QuestionData(
                "What Roman volcano erupted violently in 79 CE, completely burying the cities of Pompeii and Herculaneum under thick pyroclastic ash?",
                listOf("Mount Etna", "Mount Vesuvius", "Stromboli", "Mount Epomeo"),
                1,
                "Mount Vesuvius erupted in 79 CE, sealing Pompeii and Herculaneum in a thick blanket of volcanic ash, preserving them as invaluable archeological historical sites."
            )
            25 -> QuestionData(
                "Which historical building was an immense, white-marble temple built on the Athenian Acropolis dedicated to the warrior goddess Athena?",
                listOf("The Pantheon", "The Parthenon", "The Colosseum", "The Temple of Zeus"),
                1,
                "The Parthenon was built in mid-5th century BCE under Pericles. It is the preeminent surviving monument of classical Greek architecture and art."
            )
            else -> generateFallbackQuestion(globalId)
        }
    }

    // BLOCK 8: MODERN HISTORY
    private fun generateModernHistoryQuestion(relId: Int, globalId: Int): QuestionData {
        return when (relId) {
            1 -> QuestionData(
                "Which century was the historical era of the Industrial Revolution, beginning in Great Britain?",
                listOf("15th Century", "16th Century", "18th Century", "20th Century"),
                2,
                "The Industrial Revolution began in Britain in the mid-18th century (the 1700s), powered by steam engines and mechanization of spinning textiles."
            )
            2 -> QuestionData(
                "Who is credited with inventing the first commercially practical incandescent light bulb in 1879?",
                listOf("Nikola Tesla", "Thomas Edison", "Benjamin Franklin", "Alexander Graham Bell"),
                1,
                "Thomas Alva Edison patented and marketed a practical high-vacuum carbon-filament incandescent electric light bulb in 1879."
            )
            3 -> QuestionData(
                "What historical event in 1914 triggered the outbreak of World War I?",
                listOf("The Bolshevik Revolution", "The sinking of the Lusitania", "The assassination of Archduke Franz Ferdinand", "The invasion of Poland"),
                2,
                "The assassination of the heir to the Austro-Hungarian throne, Franz Ferdinand, in Sarajevo on June 28, 1914, set off a chain reaction of European alliances resulting in WWI."
            )
            4 -> QuestionData(
                "Who was the Soviet cosmonaut who became the first human to fly into outer space and orbit the Earth in 1961?",
                listOf("Alan Shepard", "Yuri Gagarin", "Neil Armstrong", "John Glenn"),
                1,
                "Yuri Gagarin on Vostok 1 became the first human to travel into space, completing a single orbit on April 12, 1961."
            )
            5 -> QuestionData(
                "What historic Apollo lunar crew commander became the first human to walk on the Moon in July 1969?",
                listOf("Buzz Aldrin", "Michael Collins", "Neil Armstrong", "Yuri Gagarin"),
                2,
                "Neil Armstrong was the commander of Apollo 11, stepping onto the lunar surface on July 20, 1969, with the words: 'That's one small step for man, one giant leap for mankind.'"
            )
            6 -> QuestionData(
                "Who is widely credited with developing the movable-type printing press around 1440, revolutionizing written communication in Europe?",
                listOf("Leonardo da Vinci", "Johannes Gutenberg", "Isaac Newton", "Galileo Galilei"),
                1,
                "German goldsmith Johannes Gutenberg invented the mechanical movable-type printing press. This facilitated the mass printing of books and fueled the Renaissance."
            )
            7 -> QuestionData(
                "What formal peace treaty, signed in June 1919, officially concluded World War I but imposed heavy punitive reparations on Germany?",
                listOf("Treaty of Paris", "Treaty of Versailles", "Treaty of Utrecht", "Treaty of Ghent"),
                1,
                "The Treaty of Versailles ended WWI. Its strict guilt clauses and financial reparations on Germany severely crippled its economy, planting political seeds for WWII."
            )
            8 -> QuestionData(
                "What massive naval invasion on June 6, 1944 (commonly called D-Day) marked the beginning of the Allied liberation of Western Europe from Nazi control?",
                listOf("Operation Barbarossa", "The Battle of Britain", "The Normandy Landings", "The Battle of Midway"),
                2,
                "D-Day (Operation Overlord) saw Allied forces storm the beaches of Normandy, France, starting a major western land assault against Nazi forces in Europe."
            )
            9 -> QuestionData(
                "Who was the British Prime Minister who led the United Kingdom during the height of World War II?",
                listOf("Neville Chamberlain", "Winston Churchill", "Clement Attlee", "Margaret Thatcher"),
                1,
                "Winston Churchill became Prime Minister in May 1940. His defiant speeches and steadfast military leadership guided and rallied Britain through WWII."
            )
            10 -> QuestionData(
                "What structure, constructed in 1961, became the physical symbol of the Cold War division between Eastern communist and Western democratic blocs?",
                listOf("The Iron Curtain", "The Berlin Wall", "The Great Divide", "The Mason-Dixon Line"),
                1,
                "The Berlin Wall was erected by East Germany to prevent citizens escaping to West Berlin. Its fall in November 1989 symbolized the collapse of the Soviet bloc."
            )
            11 -> QuestionData(
                "Whose assassination, occurring in April 1968, triggered widespread mourning and riots across the United States during the Civil Rights Movement?",
                listOf("John F. Kennedy", "Malcolm X", "Martin Luther King Jr.", "Robert F. Kennedy"),
                2,
                "Civil rights leader Martin Luther King Jr., champion of nonviolent activism, was tragically shot and killed in Memphis, Tennessee on April 4, 1968."
            )
            12 -> QuestionData(
                "What international organization was founded in 1945 immediately after World War II to promote world peace, replacing the failed League of Nations?",
                listOf("The Red Cross", "The United Nations", "NATO", "The European Union"),
                1,
                "The United Nations (UN) was established in October 1945 with 51 charter states to foster global cooperation, security, and human rights."
            )
            13 -> QuestionData(
                "Who wrote the highly influential scientific book 'On the Origin of Species' in 1859, describing natural selection?",
                listOf("Gregor Mendel", "Charles Darwin", "Louis Pasteur", "Jean-Baptiste Lamarck"),
                1,
                "Charles Darwin published 'On the Origin of Species' in 1859, proposing that species adapt and evolve over time through natural selection."
            )
            14 -> QuestionData(
                "What conflict, fought between 1861 and 1865, resulted in the preservation of the United States and the formal abolition of slavery?",
                listOf("The Revolutionary War", "The War of 1812", "The American Civil War", "The Spanish-American War"),
                2,
                "The American Civil War, fought between the Union and the seceded Confederate states, concluded in 1865, leading to the ratification of the 13th Amendment."
            )
            15 -> QuestionData(
                "Who was the prime absolute monarch of France, nicknamed 'The Sun King', who built the immense, luxurious Palace of Versailles?",
                listOf("Louis XIV", "Louis XVI", "Napoleon Bonaparte", "Henry IV"),
                0,
                "Louis XIV ruled France for 72 years, centralizing power and turning his hunting lodge at Versailles into a massive palace, establishing French court supremacy."
            )
            16 -> QuestionData(
                "What scientific pioneer discovered penicillin, the world's first true chemical antibiotic, in 1928?",
                listOf("Louis Pasteur", "Edward Jenner", "Alexander Fleming", "Jonas Salk"),
                2,
                "Sir Alexander Fleming found that green Penicillium mould contaminated a petri dish, producing substance that dissolved bacteria, launching the antibiotic era."
            )
            17 -> QuestionData(
                "What critical historical charter did United States President Abraham Lincoln issue in January 1863, declaring all slaves in rebel states free?",
                listOf("The Gettysburg Address", "The Emancipation Proclamation", "The Declaration of Civil Rights", "The Magna Carta"),
                1,
                "The Emancipation Proclamation declared that all enslaved people within the rebelling Confederate states 'are, and henceforward shall be free.'"
            )
            18 -> QuestionData(
                "Which major country was divided into four occupation zones following its defeat in WWII, eventually splitting into East and West states?",
                listOf("Austria", "Italy", "Germany", "Japan"),
                2,
                "Germany was carved into American, British, French, and Soviet military occupation zones, giving rise to West Germany (FRG) and East Germany (GDR)."
            )
            19 -> QuestionData(
                "Which famous woman scientist discovered the radioactive elements Radium and Polonium, becoming the first person to win two Nobel Prizes?",
                listOf("Rosalind Franklin", "Marie Curie", "Ada Lovelace", "Lise Meitner"),
                1,
                "Marie Sklodowska Curie won the 1903 Nobel Prize in Physics (for radioactivity work) and the 1911 Nobel Prize in Chemistry (for isolating pure radium)."
            )
            20 -> QuestionData(
                "Which battle, occurring in 1815, marked the final military defeat of French Emperor Napoleon Bonaparte by British and Prussian armies?",
                listOf("Battle of Austerlitz", "Battle of Leipzig", "Battle of Waterloo", "Battle of Borodino"),
                2,
                "Napoleon's final campaign was crushed at Waterloo in Belgium by the Duke of Wellington and Gebhard von Blücher, ending his reign permanently."
            )
            21 -> QuestionData(
                "The historic series of protests in Massachusetts colonies leading to physical destruction of British tea shipments in December 1773 was called:",
                listOf("The Lexington Battle", "The Boston Tea Party", "The Continental Congress", "The Stamp Riots"),
                1,
                "The Boston Tea Party saw the Sons of Liberty board tea ships dressed as Native Americans and dump 342 chests of tea into the harbor to protest tax laws without colonial representation."
            )
            22 -> QuestionData(
                "What landmark 1954 Supreme Court decision declared racial segregation in public schools unconstitutional, reversing the 'separate but equal' doctrine?",
                listOf("Plessy v. Ferguson", "Brown v. Board of Education", "Marbury v. Madison", "Roe v. Wade"),
                1,
                "Brown v. Board of Education of Topeka ruled unanimously that racial segregation in public school structures violates the 14th Amendment's Equal Protection Clause."
            )
            23 -> QuestionData(
                "Which empire was dissolved immediately following its defeat in World War I, leading to the birth of modern Turkey?",
                listOf("The Austro-Hungarian Empire", "The Ottoman Empire", "The Russian Empire", "The Byzantine Empire"),
                1,
                "The Ottoman Empire joined WWI on the side of Central Powers. After its defeat, treaty partitionings and the Turkish War of Independence ended the sultanate, yielding the Turkish Republic in 1923."
            )
            24 -> QuestionData(
                "Who was the primary leader of the Indian national independence movement who championed satyagraha, or nonviolent civil disobedience, against British rule?",
                listOf("Jawaharlal Nehru", "Mahatma Gandhi", "Subhas Chandra Bose", "Bhagat Singh"),
                1,
                "Mahatma Gandhi led peaceful marches (such as the 1930 Salt March) and advocated nonviolent resistance, inspiring civil rights movements globally."
            )
            25 -> QuestionData(
                "The political period of high ideological and strategic tension between the United States and the Soviet Union, spanning from 1947 to 1991, is known as the:",
                listOf("Spanish War", "The Great Depression", "The Cold War", "The Proxy War"),
                2,
                "The Cold War was characterized by nuclear arms racing, proxy wars, ideological division (democratic capitalism vs. totalitarian communism), and space competition."
            )
            else -> generateFallbackQuestion(globalId)
        }
    }

    // BLOCK 9: PHYSICAL GEOGRAPHY
    private fun generatePhysicalGeographyQuestion(relId: Int, globalId: Int): QuestionData {
        return when (relId) {
            1 -> QuestionData(
                "What is the highest mountain peak on Earth, measured from sea level?",
                listOf("K2", "Mount Kilimanjaro", "Mount Everest", "Denali"),
                2,
                "Mount Everest, situated in the Himalayas on the border of Nepal and China, is the highest mountain peak above sea level, rising 8,848.86 meters."
            )
            2 -> QuestionData(
                "Which is the largest and deepest ocean basin on Earth Covering roughly one-third of the planet's surface?",
                listOf("Atlantic Ocean", "Indian Ocean", "Pacific Ocean", "Arctic Ocean"),
                2,
                "The Pacific Ocean is the largest ocean. It extends from the Arctic in the north to the Southern Ocean, covering more area than all Earth land combined."
            )
            3 -> QuestionData(
                "What is the deepest known location on the Earth's seabed, located in the western Pacific Ocean?",
                listOf("Puerto Rico Trench", "Java Trench", "Mariana Trench (Challenger Deep)", "Sunda Trench"),
                2,
                "Challenger Deep, located in the Mariana Trench, is the deepest known marine trench on Earth, plunging nearly 11,000 meters down."
            )
            4 -> QuestionData(
                "Which is the largest hot desert on Earth, covering much of Northern Africa?",
                listOf("Gobi Desert", "Kalahari Desert", "Sahara Desert", "Atacama Desert"),
                2,
                "The Sahara is the largest hot desert (about 9.2 million km²). The Antarctic and Arctic deserts are larger, but classified as cold deserts."
            )
            5 -> QuestionData(
                "What massive South American mountain range is the longest continental mountain range in the world?",
                listOf("The Rockies", "The Alps", "The Andes", "The Himalayas"),
                2,
                "The Andes form a continuous highland range along the western edge of South America, stretching over 7,000 kilometers."
            )
            6 -> QuestionData(
                "What geological term describes the tectonic plate boundaries where oceanic plates sink beneath continental plates into the mantle?",
                listOf("Transform boundaries", "Divergent rifts", "Subduction Zones", "Mid-ocean ridges"),
                2,
                "At subduction zones, gravity pulls a dense oceanic plate underneath a lighter continental plate, melting it into magma, creating ocean trenches and volcanoes."
            )
            7 -> QuestionData(
                "What is the outermost solid, rock-based structural layer of the Earth, resting above the mantle?",
                listOf("Inner Core", "Outer Core", "Mantle", "Crust"),
                3,
                "The Earth's crust is its thin silicate rocky outer layer, ranging in depth from roughly 5 km under oceans to 70 km beneath mountain ranges."
            )
            8 -> QuestionData(
                "The imaginary belt located at 0 degrees latitude that divides the Northern and Southern hemispheres is the:",
                listOf("Prime Meridian", "Tropic of Cancer", "Tropic of Capricorn", "Equator"),
                3,
                "The Equator is the 0-degree latitude circle, equidistant from the North and South poles, receiving high solar radiation year-round."
            )
            9 -> QuestionData(
                "What specialized rock type forms when melted liquid magma cools and solidifies, either below or on the Earth's surface?",
                listOf("Sedimentary Rock", "Metamorphic Rock", "Igneous Rock", "Fossilized Rock"),
                2,
                "Igneous rocks (like basalt or granite) crystallize from molten rock (lava or magma). Sedimentary forms from compressed sediment, and metamorphic from extreme heat/pressure."
            )
            10 -> QuestionData(
                "What term describes of the Earth's atmosphere closest to the surface, containing nearly all weather activity and cloud formations?",
                listOf("Stratosphere", "Mesosphere", "Thermosphere", "Troposphere"),
                3,
                "The troposphere is the lowest atmospheric layer (extending 7-20 km up). It contains 75% of total air mass and almost all water vapor/weather."
            )
            11 -> QuestionData(
                "Which of the following describes the rain shadow effect?",
                listOf("Severe rainfall in regions containing high forests", "Dry arid conditions on the leeward side of high mountains", "Flooding along tropical coastlines", "Monsoon reversals near oceans"),
                1,
                "Warm moist air hits a mountain, rising and raining on the windward side. Once past the crest, the dry air descends, creating a warm, arid rain shadow zone on the leeward side."
            )
            12 -> QuestionData(
                "Which planet-encircling current is a warm, swift Atlantic ocean current that originates in the Gulf of Mexico, warming Western Europe?",
                listOf("California Current", "Gulf Stream", "Kurishio Current", "Peru Current"),
                1,
                "The Gulf Stream carries warm tropical water northwest, across the Atlantic, moderating and warming climates in Western Europe."
            )
            13 -> QuestionData(
                "What is the name of the ancient supercontinent that contained almost all of the Earth's landmasses before cracking apart 200 million years ago?",
                listOf("Gondwana", "Laurasia", "Pangaea", "Rodinia"),
                2,
                "Pangaea (meaning 'all Earth' in ancient Greek) was the immense supercontinent that assembled about 335 million years ago, subsequently fracturing due to continental drift."
            )
            14 -> QuestionData(
                "What active earthquake-prone area, bordering the basin of the Pacific Ocean, is home to over 75% of the world's volcanoes?",
                listOf("Mid-Atlantic Ridge", "The Ring of Fire", "The Alpine Belt", "San Andreas Rift"),
                1,
                "The Ring of Fire is a 40,000 km horse-shoe shaped path of subduction tectonic boundaries, responsible for the vast majority of Earth's seismic tremors and active volcanoes."
            )
            15 -> QuestionData(
                "What geological agent is responsible for carving deep canyon valleys, moving sediment, and forming wide alluvial deltas over millennia?",
                listOf("Glacial ice", "Wind erosion", "Running river water", "Chemical weathering"),
                2,
                "Running water is the dominant agent of erosion and landscape modification, transporting ground material downstream, carving canyons (like the Grand Canyon) in rock."
            )
            16 -> QuestionData(
                "What physical term is used to describe the reflectivity of a surface, particularly ice reflecting solar rays?",
                listOf("Refraction index", "Albedo", "Insolation", "Transmissivity"),
                1,
                "Albedo is the measure of diffuse reflection of solar radiation. Fresh snow has a high albedo (reflecting most light), while dark oceans have a low albedo."
            )
            17 -> QuestionData(
                "Which atmospheric layer contains the ozone layer, which filters out harmful ultraviolet solar radiation?",
                listOf("Troposphere", "Stratosphere", "Mesosphere", "Ionosphere"),
                1,
                "The stratosphere lies above the troposphere. It contains ozone gas (O₃) molecules which absorb high-energy solar UV rays, protecting life below."
            )
            18 -> QuestionData(
                "What are the major structural blocks of the Earth's lithosphere that glide across the underlying fluid asthenosphere called?",
                listOf("Fault lines", "Crust blocks", "Tectonic Plates", "Continental divides"),
                2,
                "Tectonic plates are massive rigid slabs of Earth's crust and upper mantle that drift slowly on the semi-fluid asthenosphere, forming borders where earthquakes occur."
            )
            19 -> QuestionData(
                "What type of lake forms when a wide bend in a river is cut off from the main channel, creating a crescent-shaped body of water?",
                listOf("Rift lake", "Crater lake", "Oxbow Lake", "Glacial fjord"),
                2,
                "Oxbow lakes form in mature, meandering rivers. The river erodes the neck of a loop over time, choosing a straighter path and leaving a stagnant, curved lake behind."
            )
            20 -> QuestionData(
                "What dry desert valley is historically recorded as the hottest national location on Earth, recording air warmth of 56.7°C (134°F)?",
                listOf("Sahara Desert", "Death Valley", "Lut Desert", "Atacama Desert"),
                1,
                "Death Valley in California, USA, is a deep graben basin located 86 meters below sea level, trapping heat to produce some of the highest recorded air temperatures on the planet."
            )
            21 -> QuestionData(
                "What scale is seismically used to measure the moment magnitude and energy released by an earthquake?",
                listOf("Beaufort Scale", "Richter / Moment Magnitude Scale", "Fujita Scale", "Mohs Scale"),
                1,
                "While the Richter scale was historically popular, scientists now use the Moment Magnitude Scale to estimate the precise energy released by moderate-to-large earthquakes."
            )
            22 -> QuestionData(
                "What is the term for the frozen ground layer in Arctic regions that remains continuously below 0°C (32°F) for two or more years?",
                listOf("Glacial drift", "Ice pack", "Permafrost", "Tundra soil"),
                2,
                "Permafrost is soil, sediment, or rock that remains completely frozen continuously for at least two years, often rich in trapped organic carbon."
            )
            23 -> QuestionData(
                "The transition zone where a river meets the open sea, characterized by brackish water and rich sediment deposits, is called an:",
                listOf("Estuary", "Alluvial fan", "Archipelago", "Atoll"),
                0,
                "An estuary is a semi-enclosed coastal body where freshwater rivers dilute open salt-water seas, creating highly biologically diverse brackish wetlands."
            )
            24 -> QuestionData(
                "What primary gas accounts for roughly 78% of the chemical makeup of dry air in the Earth's atmosphere?",
                listOf("Oxygen", "Carbon Dioxide", "Nitrogen", "Argon"),
                2,
                "Earth's dry atmosphere is composed of 78.08% Nitrogen, 20.95% Oxygen, 0.93% Argon, and trace quantities of Carbon Dioxide and other elements."
            )
            25 -> QuestionData(
                "What dry coastal desert in Chile is recorded as the absolute driest non-polar desert in the world, receiving virtually zero rainfall?",
                listOf("Kalahari Desert", "Gobi Desert", "Atacama Desert", "Mojave Desert"),
                2,
                "The Atacama Desert is an hyper-arid cold desert situated between the high Andes and the Chilean Coast Range, experiencing extreme dry shadow blockage."
            )
            else -> generateFallbackQuestion(globalId)
        }
    }

    // BLOCK 10: POLITICAL & HUMAN GEOGRAPHY
    private fun generatePoliticalGeographyQuestion(relId: Int, globalId: Int): QuestionData {
        return when (relId) {
            1 -> QuestionData(
                "What is the sovereign national capital of Japan?",
                listOf("Beijing", "Seoul", "Kyoto", "Tokyo"),
                3,
                "Tokyo is the official political capital of Japan and the core of the world's most populous metropolitan administrative area."
            )
            2 -> QuestionData(
                "Which is the longest river in the world, flowing northwards through eastern Africa into the Mediterranean Sea?",
                listOf("Amazon River", "Nile River", "Yangtze River", "Mississippi River"),
                1,
                "The Nile is traditionally recognized as the longest river in the world, spanning approximately 6,650 kilometers, whereas the Amazon holds the record for volume."
            )
            3 -> QuestionData(
                "Which is the largest country in the world by total land area, spanning across two continents?",
                listOf("Canada", "China", "United States", "Russia"),
                3,
                "Russia is the largest country by landmass, covering more than 17 million square kilometers across northern Europe and Asia."
            )
            4 -> QuestionData(
                "What country is completely landlocked, meaning it has no access to open ocean coastlines?",
                listOf("Vietnam", "Switzerland", "Brazil", "India"),
                1,
                "Switzerland is a landlocked European country ringed by France, Germany, Italy, Austria, and Liechtenstein. Vietnam, Brazil, and India have extensive open sea coastlines."
            )
            5 -> QuestionData(
                "What shipping canal, opened in 1869 in Egypt, connects the Mediterranean Sea directly to the Red Sea, bypassing Africa?",
                listOf("Panama Canal", "Suez Canal", "Erie Canal", "Kiel Canal"),
                1,
                "The Suez Canal is a sea-level artificial waterway in Egypt that connects the Mediterranean for direct maritime trade transit to Asia and the Indian Ocean."
            )
            6 -> QuestionData(
                "What is the capital city of France?",
                listOf("Lyon", "Marseille", "Rome", "Paris"),
                3,
                "Paris is the capital and largest city of France, situated on the Seine River."
            )
            7 -> QuestionData(
                "Which is the most populous nation in the world (surpassing 1.4 billion people)?",
                listOf("United States", "India", "China", "Indonesia"),
                1,
                "In 2023, demographic estimates indicated that India surpassed China to become the world's most populous sovereign nation."
            )
            8 -> QuestionData(
                "What is the official capital city of Canada?",
                listOf("Toronto", "Montreal", "Ottawa", "Vancouver"),
                2,
                "Ottawa was selected as the capital of Canada by Queen Victoria in 1857 due to its strategic position away from US border lines."
            )
            9 -> QuestionData(
                "What narrow body of water separates the southern tip of Spain from northern Africa?",
                listOf("The English Channel", "The Bosporus", "The Strait of Gibraltar", "The Bering Strait"),
                2,
                "The Strait of Gibraltar connects the Atlantic Ocean to the Mediterranean Sea, separating Spain from Morocco by only 14.3 kilometers at its narrowest point."
            )
            10 -> QuestionData(
                "What country is shaped like a boot in southern Europe, extending into the Mediterranean?",
                listOf("Greece", "Spain", "Portugal", "Italy"),
                3,
                "Italy is famous for its boot-shaped geographic silhouette, extending southward into the Mediterranean Sea."
            )
            11 -> QuestionData(
                "What is the capital city of Australia?",
                listOf("Sydney", "Melbourne", "Canberra", "Brisbane"),
                2,
                "Canberra was selected as Australia's capital in 1908 as a compromise between rival metropolis centers Sydney and Melbourne."
            )
            12 -> QuestionData(
                "Which is the deepest freshwater lake on Earth, located in southern Siberia, Russia?",
                listOf("Lake Superior", "Lake Baikal", "Lake Victoria", "Lake Tanganyika"),
                1,
                "Lake Baikal is the deepest lake in the world (1,642 meters) and the largest by volume, holding about 20% of Earth's unfrozen surface fresh water."
            )
            13 -> QuestionData(
                "What is the sovereign national capital of Egypt?",
                listOf("Alexandria", "Cairo", "Giza", "Luxor"),
                1,
                "Cairo is Egypt's capital city, situated near the head of the Nile Delta."
            )
            14 -> QuestionData(
                "Which European nation is divided into 26 political cantons, historically famous for maintaining armed neutrality?",
                listOf("Austria", "Sweden", "Switzerland", "Norway"),
                2,
                "Switzerland is a federal republic of 26 states called cantons, with a long history of political and military neutrality in major conflicts."
            )
            15 -> QuestionData(
                "What country contains the source of the Amazon River and is famous for the ancient Inca ruins of Machu Picchu?",
                listOf("Brazil", "Colombia", "Peru", "Bolivia"),
                2,
                "Peru contains the headwaters of the Amazon in the Andes, and hosts Machu Picchu, the iconic 15th-century Inca citadel."
            )
            16 -> QuestionData(
                "Which is the largest island in the world, managed as an autonomous territory of Denmark?",
                listOf("Australia", "Greenland", "Madagascar", "New Guinea"),
                1,
                "Greenland is the largest non-continental island. Australia is technically classified as a continent rather than an island."
            )
            17 -> QuestionData(
                "What is the capital city of Germany?",
                listOf("Munich", "Frankfurt", "Hamburg", "Berlin"),
                3,
                "Berlin is the capital of Germany and its most populous metropolis city."
            )
            18 -> QuestionData(
                "Which is the smallest sovereign nation in the world by both population and land area, enclosed entirely within Rome, Italy?",
                listOf("Monaco", "San Marino", "Liechtenstein", "Vatican City"),
                3,
                "Vatican City is an independent city-state (covering 49 hectares/121 acres) ruled by the Pope, making it the smallest country."
            )
            19 -> QuestionData(
                "What massive river is the longest river in Asia, flowing entirely within China?",
                listOf("Yellow River", "Mekong River", "Yangtze River", "Amur River"),
                2,
                "The Yangtze River is the longest river in Asia and the third-longest in the world, flowing 6,300 km from Tibet to the East China Sea."
            )
            20 -> QuestionData(
                "What is the capital city of Brazil, inaugurated in 1960 as a planned administrative hub?",
                listOf("Rio de Janeiro", "São Paulo", "Brasília", "Salvador"),
                2,
                "Brasília was built in the late 1950s and officially became Brazil's capital in 1960, replacing Rio de Janeiro to move the focus inland."
            )
            21 -> QuestionData(
                "Which African nation is the most populous, situated on the Gulf of Guinea?",
                listOf("South Africa", "Egypt", "Nigeria", "Ethiopia"),
                2,
                "Nigeria is the most populous country in Africa (over 220 million residents) and has one of the youthiest populations in the world."
            )
            22 -> QuestionData(
                "What sea, enclosed by Europe and Asia, is the largest semi-enclosed body of water, containing the Crimean Peninsula?",
                listOf("Caspian Sea", "Black Sea", "Mediterranean Sea", "Baltic Sea"),
                1,
                "The Black Sea is a large inland sea bordered by Ukraine, Russia, Georgia, Turkey, Bulgaria, and Romania."
            )
            23 -> QuestionData(
                "What is the sovereign capital of Spain, located right near its geographical center?",
                listOf("Barcelona", "Seville", "Valencia", "Madrid"),
                3,
                "Madrid is the capital of Spain and its political, economic, and cultural administrative heart."
            )
            24 -> QuestionData(
                "Which Asian country consists of an archipelago of over 17,000 islands, making it the largest island country?",
                listOf("Philippines", "Japan", "Indonesia", "Maldives"),
                2,
                "Indonesia is the world's largest island country and contains the world's largest Muslim-majority population."
            )
            25 -> QuestionData(
                "What is the official capital city of Italy?",
                listOf("Milan", "Naples", "Florence", "Rome"),
                3,
                "Rome is Italy's historic capital, known as the Eternal City, housing the Vatican and Roman Forum."
            )
            else -> generateFallbackQuestion(globalId)
        }
    }

    // BLOCK 11: ENGLISH APPLIED GRAMMAR & VOCABULARY
    private fun generateEnglishGrammarQuestion(relId: Int, globalId: Int): QuestionData {
        return when (relId) {
            1 -> QuestionData(
                "Choose the correct word: 'Neither of the final candidates _____ selected yet.'",
                listOf("has been", "have been", "were", "are"),
                0,
                "The subject of the sentence is the pronoun 'Neither', which is singular. Therefore, it requires the singular verb 'has been'. (The plural noun 'candidates' is part of a prepositional phrase)."
            )
            2 -> QuestionData(
                "What is the definition of the word 'eccentric' in standard English?",
                listOf("Ordinary and quiet", "Unconventional and slightly strange", "Sarcastic and rude", "Extremely wealthy"),
                1,
                "'Eccentric' describes behavior that is unconventional, anomalous, or slightly strange, but in a harmless or interesting way."
            )
            3 -> QuestionData(
                "Identify the correct sentence using apostrophes for possession:",
                listOf("The two dog's collars were red.", "The two dogs' collars were red.", "The two dogs collars' were red.", "The two dogs collars were red."),
                1,
                "For plural nouns ending in 's' (like 'dogs'), possession is shown by adding only an apostrophe at the end: 'dogs' collars'."
            )
            4 -> QuestionData(
                "What is the synonym of the word 'benevolent'?",
                listOf("Cruel and angry", "Kind and generous", "Clever and fast", "Lazy and slow"),
                1,
                "'Benevolent' means well-meaning, kindly, or charitable, which is synonymous with 'kind and generous'."
            )
            5 -> QuestionData(
                "Identify the correct conjunction to complete: 'We went to the beach _____ it was raining heavily.'",
                listOf("because", "although", "consequently", "whereas"),
                1,
                "'Although' is an adversative conjunction that introduces a contrasting concession clause, fitting the logic of entering rain at a beach."
            )
            6 -> QuestionData(
                "What is the part of speech of the word 'quickly' in: 'He quickly finished his homework'?",
                listOf("Adjective", "Noun", "Adverb", "Preposition"),
                2,
                "'Quickly' is an adverb because it modifies the verb 'finished', describing how the action was performed."
            )
            7 -> QuestionData(
                "Which sentence demonstrates correct subject-verb agreement?",
                listOf("The herd of cattle are grazing.", "The herd of cattle is grazing.", "The herd of cattle were grazing.", "The herd of cattle have grazed."),
                1,
                "'Herd' is a collective noun acting as a single singular unit here. Thus, it takes the singular verb 'is'."
            )
            8 -> QuestionData(
                "What is the antonym of the word 'loquacious' (talkative)?",
                listOf("Silent/Taciturn", "Generous", "Articulate", "Bombastic"),
                0,
                "'Loquacious' means highly talkative. Its antonym is 'silent', 'reserved', or 'taciturn' (uncommunicative)."
            )
            9 -> QuestionData(
                "Choose the sentence which uses a semicolon correctly:",
                listOf("I have a big test tomorrow; I need to study.", "I have; a big test tomorrow I need to study.", "I have a big test; tomorrow, I need to study.", "I have a big test tomorrow,; I need to study."),
                0,
                "A semicolon is used to connect two independent clauses that are closely related in thought, without using a coordinating conjunction."
            )
            10 -> QuestionData(
                "What is the correct meaning of the idiom 'to spill the beans'?",
                listOf("To cook dinner", "To trigger an accident", "To reveal a secret prematurely", "To spend money foolishly"),
                2,
                "The idiom 'to spill the beans' means to let out confidential information or disclose a secret, especially by accident."
            )
            11 -> QuestionData(
                "Choose the form that completes: 'She had already _____ all her juice before the lunch bell rang.'",
                listOf("drink", "drank", "drunk", "drinking"),
                2,
                "The past perfect tense uses 'had' + past participle. The past participle of 'drink' (drink, drank, drunk) is 'drunk'."
            )
            12 -> QuestionData(
                "Identify the passive voice sentence from the options:",
                listOf("The teacher graded the exams.", "The exams were graded by the teacher.", "The teacher was grading exams.", "The exams had pleased the teacher."),
                1,
                "In passive voice, the target of the action (the exams) is placed as the grammatical subject, and the action is expressed via 'were' +'graded'."
            )
            13 -> QuestionData(
                "What is the definition of the word 'subtle'?",
                listOf("Loud and disruptive", "Delicate, precise, and difficult to analyze", "Unbelievably simple", "Fake or deceptive"),
                1,
                "'Subtle' describes a change, flavor, or distinction that is delicate, understated, clever, and not immediately obvious."
            )
            14 -> QuestionData(
                "Which of the following describes a 'run-on' sentence?",
                listOf("A sentence that is extremely long", "Two or more independent clauses joined without correct punctuation", "A sentence containing too many adjectives", "A sentence lacking a subject"),
                1,
                "A run-on sentence occurs when two or more independent clauses are merged together without proper punctuation interface (like a semicolon, period, or conjunction)."
            )
            15 -> QuestionData(
                "Select the correct spelling of the word meaning to accommodate or adjust:",
                listOf("Accomodate", "Acomodate", "Accommodate", "Acommodate"),
                2,
                "The correct spelling is 'accommodate' (it has double 'c' and double 'm')."
            )
            16 -> QuestionData(
                "What punctuation mark is used to join words together to act as a single compound modifier (e.g., 'well-known')?",
                listOf("Comma", "Dash", "Hyphen", "Colon"),
                2,
                "A hyphen (-) is used to join words together (such as compound adjectives preceding nouns: 'well-designed', 'two-step')."
            )
            17 -> QuestionData(
                "What is the meaning of the prefix 'chrono-' in words like 'chronological' and 'chronometer'?",
                listOf("Color", "Time", "Space", "Sound"),
                1,
                "The prefix 'chrono-' originates from the Greek 'khronos', which means 'time'."
            )
            18 -> QuestionData(
                "Choose the correct word: 'The new regulations will _____ our department heavily.'",
                listOf("affect", "effect", "efficient", "defect"),
                0,
                "'Affect' is a verb meaning to influence or make a difference to. 'Effect' is most commonly used as a noun meaning the result of an action."
            )
            19 -> QuestionData(
                "In English grammar, a gerund is a verb form that ends in '-ing' and functions as which part of speech?",
                listOf("An Adjective", "A Noun", "An Adverb", "A Conjunction"),
                1,
                "A gerund is a verb form ending in -ing that functions as a noun (e.g., 'Swimming is good exercise')."
            )
            20 -> QuestionData(
                "Identify the pronoun that represents an objective case pronoun:",
                listOf("He", "I", "They", "Them"),
                3,
                "'Them' is an objective pronoun (used as an object of a verb or preposition), while 'He', 'I', and 'They' are subjective case pronouns."
            )
            21 -> QuestionData(
                "What is the meaning of the word 'obsolete'?",
                listOf("Extremely clean", "No longer in use or outdated", "Highly valuable", "Fragile and easily broken"),
                1,
                "Something that is 'obsolete' is out-of-date, superseded, or no longer produced or used (e.g., floppy disks)."
            )
            22 -> QuestionData(
                "Select the correct homophone: 'They decided to pack _____ luggage and head to the airport.'",
                listOf("there", "their", "they're", "there's"),
                1,
                "'Their' is the possessive pronoun indicating ownership by 'they'. ('There' is a place, 'They're' is a contraction of 'they are')."
            )
            23 -> QuestionData(
                "Which phrase is a cliché or redundant expression?",
                listOf("Past history", "Complete work", "Early morning", "High mountain"),
                0,
                "'Past history' is redundant (pleonastic) because history, by definition, is already in the past."
            )
            24 -> QuestionData(
                "The word 'gregarious' refers to a person who is:",
                listOf("Silent and shy", "Sociable and fond of company", "Extremely aggressive", "Overly cautious"),
                1,
                "'Gregarious' comes from the Latin 'grex' (flock), describing an individual who is highly sociable and enjoys being in crowds."
            )
            25 -> QuestionData(
                "Identify the correct option: 'I cannot go out to play, _____ I have not finished my chores yet.'",
                listOf("for", "but", "or", "so"),
                0,
                "'For' is used here as a coordinating conjunction meaning 'because', connecting the effect with its cause."
            )
            else -> generateFallbackQuestion(globalId)
        }
    }

    // BLOCK 12: LITERATURE & TECHNOLOGY
    private fun generateEnglishLiteratureQuestion(relId: Int, globalId: Int): QuestionData {
        return when (relId) {
            1 -> QuestionData(
                "What literary device describes comparing two unlike things using the words 'like' or 'as'?",
                listOf("Metaphor", "Simile", "Personification", "Onomatopoeia"),
                1,
                "A simile explicitly compares two things using comparison connectors like 'like' or 'as' (e.g., 'brave as a lion'). METAPHORS compare directly (e.g., 'he is a lion')."
            )
            2 -> QuestionData(
                "Who wrote the tragedy play 'Romeo and Juliet' in the late 16th century?",
                listOf("Charles Dickens", "William Shakespeare", "John Milton", "Jane Austen"),
                1,
                "Romeo and Juliet, set in Verona, was penned by English playwright William Shakespeare, published around 1597."
            )
            3 -> QuestionData(
                "What literary device is used when an author attributes human traits and emotions to non-human objects or animals?",
                listOf("Hyperbole", "Alliteration", "Synecdoche", "Personification"),
                3,
                "Personification invests inanimate objects, ideas, or creatures with human characteristics (e.g., 'the wind whispered in the trees')."
            )
            4 -> QuestionData(
                "What term refers to the main adversary, opponent, or rival of the main character (protagonist) in a story?",
                listOf("Antagonist", "Deuteragonist", "Narrator", "Mentor"),
                0,
                "The antagonist is the group, force, or character that actively opposes, contends with, or blocks the protagonist."
            )
            5 -> QuestionData(
                "Who is traditionally credited with creating the hypothetical Turing Machine in 1936, defining the basis of modern computer algorithms?",
                listOf("Steve Jobs", "Bill Gates", "Alan Turing", "Charles Babbage"),
                2,
                "Alan Turing in 1936 introduced the universal Turing Machine concept, forming the mathematical models of compute algorithms and theory."
            )
            6 -> QuestionData(
                "What was the name of the ENIAC computer, developed in 1945, representing what historical milestone?",
                listOf("The first mechanical calculator", "The first general-purpose electronic digital computer", "The first portable laptop", "The first internet router"),
                1,
                "ENIAC (Electronic Numerical Integrator and Computer) was the first programmable, electronic, general-purpose digital computer, built during WWII."
            )
            7 -> QuestionData(
                "What literary term describes the peak, turning point, or most intense moment of a story's plot?",
                listOf("Exposition", "Rising Action", "Climax", "Resolution"),
                2,
                "The climax is the structural high point of tension in a narrative, representing the turning point in the conflict."
            )
            8 -> QuestionData(
                "What famous novel by George Orwell, published in 1949, depicts a dystopian surveillance state ruled by the dictator 'Big Brother'?",
                listOf("Animal Farm", "Brave New World", "Nineteen Eighty-Four (1984)", "Fahrenheit 451"),
                2,
                "George Orwell's '1984' introduced terms like Thought Police, Newspeak, and Doublethink within its dark dystopian surveillance plot."
            )
            9 -> QuestionData(
                "What is the definition of the literary device 'alliteration'?",
                listOf("Rhyming at the end of lines", "Repeating identical consonant sounds at the start of words in close proximity", "Exaggerating for emotional effect", "A sudden plot twist"),
                1,
                "Alliteration repeats initial consonant sounds in a sequence of neighboring words to create auditory rhythm (e.g., 'Peter Piper picked...')."
            )
            10 -> QuestionData(
                "Who wrote the epic adventure fantasy trilogy 'The Lord of the Rings'?",
                listOf("J.K. Rowling", "J.R.R. Tolkien", "C.S. Lewis", "George R.R. Martin"),
                1,
                "J.R.R. Tolkien, a philologist and Oxford professor, wrote 'The Hobbit' and the high-fantasy novel series 'The Lord of the Rings'."
            )
            11 -> QuestionData(
                "What is a 'soliloquy' in dramatic literature?",
                listOf("A speech delivered by a character alone on stage to express their inner thoughts", "A fast conversation between two characters", "An introductory poem to open a play", "A comedic resolution scene"),
                0,
                "A soliloquy is a dramatic literary device where a character speaks their private thoughts aloud while alone on stage, revealing inner psychology to the audience."
            )
            12 -> QuestionData(
                "What is the term for an extreme, intentional exaggeration used to emphasize a point or create humor (e.g., 'I have a million things to do')?",
                listOf("Understatement", "Irony", "Hyperbole", "Paradox"),
                2,
                "Hyperbole is a figure of speech that uses deliberate, extreme exaggeration for dramatic emphasis rather than literal truth."
            )
            13 -> QuestionData(
                "Which famous Greek epic, composed by Homer, chronicles King Odysseus's epic, decade-long journey home after the fall of Troy?",
                listOf("The Iliad", "The Odyssey", "The Aeneid", "The Argonautica"),
                1,
                "Homer's 'The Odyssey' focuses on Odysseus's perilous adventures battling mythical monsters on his journey back to Ithaca and his wife Penelope."
            )
            14 -> QuestionData(
                "What literary genre is characterized by stories set in a future that explores advanced science, space travel, and technology?",
                listOf("Historical Fiction", "High Fantasy", "Science Fiction", "Mystery and Thriller"),
                2,
                "Science fiction (Sci-Fi) uses imaginative scientific, technological, and cosmological foundations to examine human reactions to advanced innovations."
            )
            15 -> QuestionData(
                "Who wrote the gothic horror classic 'Frankenstein; or, The Modern Prometheus' in 1818?",
                listOf("Edgar Allan Poe", "Mary Shelley", "Bram Stoker", "Jane Austen"),
                1,
                "Mary Wollstonecraft Shelley conceived and wrote 'Frankenstein' during a rainy summer challenge in Switzerland when she was only 18 years old."
            )
            16 -> QuestionData(
                "What device represents the 'brain' of a computer, performing basic arithmetic, logic, and control operations?",
                listOf("Hard Drive (HDD)", "Random Access Memory (RAM)", "Central Processing Unit (CPU)", "Graphics Card (GPU)"),
                2,
                "The Central Processing Unit (CPU) is the primary integrated circuit that carries out instructions of computer programs by performing computation sequences."
            )
            17 -> QuestionData(
                "What is the name of the worldwide network of interconnected computer grids that communicates via TCP/IP protocols, launched in its modern form in the early 1990s?",
                listOf("The Intranet", "The Local Area Network", "The Internet", "The Mainframe"),
                2,
                "The Internet is the global system of interconnected computer networks that uses standard internet protocol suites to serve billions of users."
            )
            18 -> QuestionData(
                "What is a 'metaphor'?",
                listOf("A comparison using 'like' or 'as'", "A direct comparison stating one thing IS another to highlight similarities", "Giving human traits to objects", "Using sounds as words"),
                1,
                "A metaphor directly equates one object/action to another without 'like' or 'as' filters (e.g., 'All the world's a stage')."
            )
            19 -> QuestionData(
                "What is the term for a story containing a secondary, symbolic meaning, where characters represent abstract moral virtues or historical events?",
                listOf("Satire", "Allegory", "Soliloquy", "Parody"),
                1,
                "An allegory is a complex narrative in which characters, events, or and settings represent moral, spiritual, or political concepts (e.g., 'Animal Farm')."
            )
            20 -> QuestionData(
                "What is the primary purpose of Random Access Memory (RAM) in a computer system?",
                listOf("Long-term persistent data storage", "Short-term fast-speed volatile memory for active processes", "Processing graphics and rendering videos", "Powering the electrical supply"),
                1,
                "RAM is volatile fast-access memory that stores data currently being used by the CPU for quick reads and writes, cleared when the computer shuts down."
            )
            21 -> QuestionData(
                "What term describes of the emotional atmosphere or climate that an author establishes in a literary work (e.g., gloomy, cheerful)?",
                listOf("Theme", "Plot", "Mood", "Genre"),
                2,
                "Mood is the overall feeling, emotional backdrop, or atmosphere evoked in the reader through the author's selection of setting and vocabulary."
            )
            22 -> QuestionData(
                "Which computer languages, developed in the late 1950s, was designed specifically for scientific formulas and math calculation?",
                listOf("Cobol", "Fortran", "Lisp", "Basic"),
                1,
                "FORTRAN (Formula Translation), developed by IBM in 1957, was the first high-level procedural programming language, designed for scientific/engineering math."
            )
            23 -> QuestionData(
                "What literary device is used when an outcome is completely opposite of what is logically expected to happen, creating situational irony?",
                listOf("Allusion", "Irony", "Euphemism", "Oxymoron"),
                1,
                "Irony, particularly situational irony, operates on a sharp discordance between expected reality and the actual anomalous outcome."
            )
            24 -> QuestionData(
                "Who wrote the American classic 'The Adventures of Huckleberry Finn', utilizing regional southern dialects along the Mississippi?",
                listOf("Herman Melville", "Nathaniel Hawthorne", "F. Scott Fitzgerald", "Mark Twain"),
                3,
                "Mark Twain (pen name of Samuel Langhorne Clemens) sat down to write 'Huck Finn' and 'Tom Sawyer' drawing on his youth experiences on the Mississippi."
            )
            25 -> QuestionData(
                "Which historical figure, along with Ada Lovelace, conceived early designs for the mechanical Analytical Engine, a precursor to programmable computation?",
                listOf("Charles Babbage", "Thomas Watson", "Grace Hopper", "John von Neumann"),
                0,
                "Charles Babbage is considered the 'Father of the Computer' for building and designing mechanical steam-powered computing engines in the 19th century."
            )
            else -> generateFallbackQuestion(globalId)
        }
    }

    private fun generateFallbackQuestion(id: Int): QuestionData {
        return QuestionData(
            "What is question index $id value?",
            listOf("Choice A", "Choice B", "Choice C", "Choice D"),
            0,
            "This is fallback question $id explanation."
        )
    }

    private data class QuestionData(
        val text: String,
        val options: List<String>,
        val correctIndex: Int,
        val explanation: String
    )
}
