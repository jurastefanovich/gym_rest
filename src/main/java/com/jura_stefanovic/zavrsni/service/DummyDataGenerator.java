package com.jura_stefanovic.zavrsni.service;

import com.jura_stefanovic.zavrsni.utils.AppointmentGenerator;
import com.jura_stefanovic.zavrsni.utils.GymServiceGenerator;
import com.jura_stefanovic.zavrsni.utils.UserGenerator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DummyDataGenerator {
    private final UserGenerator userGenerator;
    private final GymServiceGenerator gymServiceGenerator;
    private final AppointmentGenerator appointmentGenerator;

    public void createAdminUser() {
       userGenerator.createAdminUser();
    }

    public void createTrainerUsers() {
        userGenerator.createTrainerUser(
                "jdoe", "securePass1", "John", "Doe", "j.doe@example.com", "0912345671",
                "John is a highly experienced personal trainer with a strong focus on individualized client care. He brings over a decade of hands-on experience in strength training, nutrition planning, and injury prevention. John is known for his client-centered approach and commitment to helping individuals of all fitness levels achieve sustainable results. His sessions are dynamic, goal-driven, and grounded in evidence-based methods that foster both physical and mental resilience."
        );

        userGenerator.createTrainerUser(
                "asmith", "securePass2", "Alice", "Smith", "alice.smith@example.com", "0912345672",
                "Alice specializes in holistic fitness programs that emphasize mind-body connection, flexibility, and long-term wellness. With a background in physiotherapy and yoga, she crafts routines that reduce stress and improve functional movement. Her warm personality and meticulous attention to detail help clients stay motivated and injury-free. Alice’s focus is not just on aesthetics but building lasting habits that support physical and emotional well-being."
        );

        userGenerator.createTrainerUser(
                "bwayne", "securePass3", "Bruce", "Wayne", "bruce.wayne@example.com", "0912345673",
                "Bruce brings unmatched intensity and discipline to his training sessions. Drawing from martial arts, strength conditioning, and endurance training, he crafts regimens suited for clients seeking elite-level fitness. With military precision and a no-nonsense attitude, Bruce focuses on accountability, perseverance, and pushing limits. His sessions demand the best and build resilience, confidence, and functional power from the ground up."
        );

        userGenerator.createTrainerUser(
                "ckent", "securePass4", "Clark", "Kent", "clark.kent@example.com", "0912345674",
                "Clark is a compassionate and driven personal trainer who empowers clients through strength-based training and motivational coaching. His approach is rooted in integrity and support, guiding clients to surpass their perceived limitations. Clark's calm demeanor and structured programs ensure consistent progress, whether clients are beginners or seasoned athletes. His sessions combine functional training with endurance work to build well-rounded, sustainable fitness."
        );

        userGenerator.createTrainerUser(
                "dprince", "securePass5", "Diana", "Prince", "diana.prince@example.com", "0912345675",
                "Diana is a powerhouse trainer who inspires through strength, grace, and wisdom. Her training sessions blend combat conditioning, high-intensity interval training (HIIT), and mobility enhancement. Diana fosters mental toughness and encourages inner strength alongside physical improvement. Her philosophy revolves around empowering clients to reclaim control of their health, believe in themselves, and achieve their fullest potential with discipline and empathy."
        );

        userGenerator.createTrainerUser(
                "pparker", "securePass6", "Peter", "Parker", "peter.parker@example.com", "0912345676",
                "Peter specializes in agility training, bodyweight control, and functional performance. Energetic and adaptive, he crafts engaging workouts suitable for all levels, often incorporating fun and science-backed innovations to boost motivation and results. His analytical mindset ensures data-driven improvement, while his friendly, approachable style makes fitness feel less intimidating. Peter thrives on helping people discover joy in movement and health."
        );

        userGenerator.createTrainerUser(
                "nromanoff", "securePass7", "Natasha", "Romanoff", "natasha.romanoff@example.com", "0912345677",
                "Natasha delivers precise, disciplined training sessions focused on endurance, agility, and core control. A master of stealth and technique, she incorporates martial arts, bodyweight control, and mental focus drills into her routines. Her training emphasizes control, patience, and peak readiness. Clients who train with Natasha build a deep understanding of their bodies and develop the mental fortitude required for advanced physical challenges."
        );

        userGenerator.createTrainerUser(
                "tstark", "securePass8", "Tony", "Stark", "tony.stark@example.com", "0912345678",
                "Tony combines advanced fitness technology with data-driven personal coaching. His methods integrate wearable metrics, strength science, and high-tech tools for precision training. With a sharp wit and relentless innovation, Tony’s clients benefit from progressive overload systems, smart recovery tracking, and individualized performance insights. His sessions are both intellectually and physically stimulating, built on a foundation of efficiency and creativity."
        );

        userGenerator.createTrainerUser(
                "srogers", "securePass9", "Steve", "Rogers", "steve.rogers.second@example.com", "0912345679",
                "Steve leads with unwavering dedication and a passion for empowering others. His coaching style is based on values of consistency, determination, and honor. Steve focuses on full-body strength development, cardiovascular endurance, and instilling confidence in his clients. His sessions promote discipline, grit, and balance, with routines that support long-term transformation and health. Steve helps clients unlock their full physical and personal potential."
        );

        userGenerator.createTrainerUser(
                "bannerb", "securePass10", "Bruce", "Banner", "bruce.banner@example.com", "0912345680",
                "Bruce blends the science of human performance with practical fitness techniques. With a background in biomechanics and kinesiology, he focuses on smart training that maximizes results while minimizing injury risk. His calm demeanor and methodical approach help clients understand the 'why' behind every movement. Bruce believes in evidence-based training, recovery, and a well-rounded program that supports both the body and the mind in harmony."
        );

        userGenerator.createTrainerUser(
                "rogersc", "securePass11", "Steve", "Rogers", "steve.rogers@example.com", "0912345681",
                "Steve brings a military-style discipline to personal training. With a strong emphasis on consistency and form, he helps clients build resilience and confidence. Steve believes that every small step leads to big changes and motivates his clients to push past perceived limits safely and sustainably."
        );

        userGenerator.createTrainerUser(
                "romanovn", "securePass12", "Natasha", "Romanov", "natasha.romanov@example.com", "0912345682",
                "Natasha combines flexibility training with core strength routines, helping clients improve posture and balance. With a background in martial arts and gymnastics, her sessions are dynamic and challenging. She promotes mental focus as much as physical conditioning."
        );

        userGenerator.createTrainerUser(
                "starkt", "securePass13", "Tony", "Stark", "tony.stark.alive@example.com", "0912345683",
                "Tony takes a tech-driven approach to training, utilizing performance tracking and data analysis to guide workouts. With a background in engineering and sports science, he customizes each program to match client goals with measurable outcomes. Tony believes fitness should be efficient, fun, and constantly evolving."
        );

        userGenerator.createTrainerUser(
                "parkerk", "securePass14", "Peter", "Parker", "peter.parker.toby@example.com", "0912345684",
                "Peter is energetic and relatable, specializing in training teens and young adults. His workouts blend agility, strength, and endurance with a strong emphasis on bodyweight training. Peter makes fitness approachable and motivating through education and enthusiasm."
        );

        userGenerator.createTrainerUser(
                "danversc", "securePass15", "Carol", "Danvers", "carol.danvers@example.com", "0912345685",
                "Carol is known for her empowering training style that encourages confidence and inner strength. With experience in both endurance sports and strength training, she tailors programs for women looking to improve fitness and mental toughness."
        );

        userGenerator.createTrainerUser(
                "rhodesj", "securePass16", "James", "Rhodes", "james.rhodes@example.com", "0912345686",
                "James integrates military training protocols with adaptive fitness strategies, making workouts accessible for clients of all ability levels. He believes in scalable progress, where every client can succeed no matter where they start."
        );

        userGenerator.createTrainerUser(
                "wilsont", "securePass17", "Sam", "Wilson", "sam.wilson@example.com", "0912345687",
                "Sam's coaching focuses on cardiovascular health, functional strength, and mobility. As a former pararescue trainer, he brings compassion and structure to his sessions. He emphasizes the importance of training for real-life movement and long-term wellness."
        );

        userGenerator.createTrainerUser(
                "maximoffw", "securePass18", "Wanda", "Maximoff", "wanda.maximoff@example.com", "0912345688",
                "Wanda blends mindful movement with high-intensity training. With a background in pilates and dance, she helps clients improve coordination, flexibility, and focus. She believes fitness is not just about the body, but a balance of mind, movement, and intention."
        );

        userGenerator.createTrainerUser(
                "langs", "securePass19", "Scott", "Lang", "scott.lang@example.com", "0912345689",
                "Scott makes fitness fun and approachable, especially for busy parents and beginners. His training style includes time-efficient workouts, practical strength routines, and a sense of humor that keeps clients motivated and engaged."
        );

        userGenerator.createTrainerUser(
                "tchallah", "securePass20", "T’Challa", "Udaku", "tchalla.udaku@example.com", "0912345690",
                "T’Challa emphasizes balance between power and grace. Drawing from traditional athletic disciplines and modern strength science, he designs programs that challenge both body and spirit. He believes in training with purpose, precision, and respect for the body’s full potential."
        );

    }

    public void createUsers() {
        userGenerator.creatingUser("bwilliams", "securePass3", "Bob", "Williams", "bob.williams@example.com", "0912345673");
        userGenerator.creatingUser("cjohnson", "securePass4", "Carol", "Johnson", "carol.johnson@example.com", "0912345674");
        userGenerator.creatingUser("dmiller", "securePass5", "David", "Miller", "david.miller@example.com", "0912345675");

        userGenerator.creatingUser("esanchez", "securePass6", "Eva", "Sanchez", "eva.sanchez@example.com", "0912345676");
        userGenerator.creatingUser("flee", "securePass7", "Frank", "Lee", "frank.lee@example.com", "0912345677");
        userGenerator.creatingUser("gmartin", "securePass8", "Grace", "Martin", "grace.martin@example.com", "0912345678");
        userGenerator.creatingUser("hclark", "securePass9", "Henry", "Clark", "henry.clark@example.com", "0912345679");
        userGenerator.creatingUser("ijames", "securePass10", "Ivy", "James", "ivy.james@example.com", "0912345680");
        userGenerator.creatingUser("jkhan", "securePass11", "Jamal", "Khan", "jamal.khan@example.com", "0912345681");
        userGenerator.creatingUser("klewis", "securePass12", "Kate", "Lewis", "kate.lewis@example.com", "0912345682");

        userGenerator.creatingUser("lgreen", "securePass13", "Liam", "Green", "liam.green@example.com", "0912345683");
        userGenerator.creatingUser("mwhite", "securePass14", "Mia", "White", "mia.white@example.com", "0912345684");
        userGenerator.creatingUser("nroberts", "securePass15", "Noah", "Roberts", "noah.roberts@example.com", "0912345685");
        userGenerator.creatingUser("oedwards", "securePass16", "Olivia", "Edwards", "olivia.edwards@example.com", "0912345686");
        userGenerator.creatingUser("pbennett", "securePass17", "Paul", "Bennett", "paul.bennett@example.com", "0912345687");
        userGenerator.creatingUser("qthomas", "securePass18", "Quinn", "Thomas", "quinn.thomas@example.com", "0912345688");
        userGenerator.creatingUser("rhill", "securePass19", "Rachel", "Hill", "rachel.hill@example.com", "0912345689");
        userGenerator.creatingUser("sscott", "securePass20", "Sean", "Scott", "sean.scott@example.com", "0912345690");
        userGenerator.creatingUser("tadams", "securePass21", "Tina", "Adams", "tina.adams@example.com", "0912345691");
        userGenerator.creatingUser("uwright", "securePass22", "Umar", "Wright", "umar.wright@example.com", "0912345692");
        userGenerator.creatingUser("vhernandez", "securePass23", "Vera", "Hernandez", "vera.hernandez@example.com", "0912345693");
    }


    public void createGymServices() {
        gymServiceGenerator.createDefaultGymServices();

        gymServiceGenerator.createGymService(
                "Personal Training",
                "A customized one-on-one fitness session tailored to your goals, whether it's weight loss, strength building, or rehabilitation. Work closely with a certified trainer who guides you through a targeted workout plan, monitors your form, and ensures safe progress at your pace.",
                3600L,
                userGenerator.getRandomTrainer(),
                true
        );

        gymServiceGenerator.createGymService(
                "Yoga",
                "A calming and restorative class focused on improving flexibility, balance, and mental clarity. Through a series of mindful postures and controlled breathing, yoga helps reduce stress, enhance body awareness, and build a deeper mind-body connection.",
                3000L,
                null,
                false
        );

        gymServiceGenerator.createGymService(
                "Pilates",
                "A low-impact yet highly effective class emphasizing core strength, stability, and flexibility. Using controlled movements and breathing techniques, Pilates enhances posture, tones muscles, and supports functional movement in daily life.",
                2800L,
                null,
                false
        );

        gymServiceGenerator.createGymService(
                "Spin Class",
                "An intense, rhythm-based indoor cycling workout that simulates hills, sprints, and endurance rides. Spin classes improve cardiovascular fitness, burn calories, and build lower-body strength — all in a supportive group setting with music and motivation.",
                2500L,
                userGenerator.getRandomTrainer(),
                false
        );

        gymServiceGenerator.createGymService(
                "CrossFit",
                "A high-intensity strength and conditioning program combining weightlifting, gymnastics, and functional movement. Each class offers varied workouts that build overall fitness, promote fat loss, and increase physical resilience.",
                3600L,
                userGenerator.getRandomTrainer(),
                false
        );

        gymServiceGenerator.createGymService(
                "Zumba",
                "A dynamic dance-based cardio workout inspired by Latin and international music. Zumba combines easy-to-follow choreography with energetic beats, offering a full-body workout that improves coordination, boosts mood, and burns calories while having fun.",
                2400L,
                userGenerator.getRandomTrainer(),
                false
        );

        gymServiceGenerator.createGymService(
                "HIIT",
                "A time-efficient training style featuring short bursts of high-intensity exercises followed by brief recovery periods. HIIT boosts metabolism, enhances cardiovascular performance, and promotes fat loss in a compact workout format.",
                1800L,
                null,
                false
        );

        gymServiceGenerator.createGymService(
                "BodyPump",
                "A barbell-based full-body strength training class that targets major muscle groups through repetitive, high-rep weightlifting routines. Perfect for building lean muscle, increasing endurance, and improving muscular definition.",
                3200L,
                null,
                false
        );

        gymServiceGenerator.createGymService(
                "Stretch & Mobility",
                "A guided session dedicated to improving joint mobility, muscle flexibility, and body awareness. Ideal for recovery days, injury prevention, and enhancing performance in other fitness activities, this class helps you move better and feel better.",
                2000L,
                userGenerator.getRandomTrainer(),
                true
        );
    }

    public void createAppointments() {
        appointmentGenerator.generateDummyGroupAppointments(200);
    }




}
