package com.jura_stefanovic.zavrsni.service;

import com.jura_stefanovic.zavrsni.utils.GymServiceGenerator;
import com.jura_stefanovic.zavrsni.utils.UserGenerator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DummyDataGenerator {
    private final UserGenerator userGenerator;
    private final GymServiceGenerator gymServiceGenerator;

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
                "srogers", "securePass9", "Steve", "Rogers", "steve.rogers@example.com", "0912345679",
                "Steve leads with unwavering dedication and a passion for empowering others. His coaching style is based on values of consistency, determination, and honor. Steve focuses on full-body strength development, cardiovascular endurance, and instilling confidence in his clients. His sessions promote discipline, grit, and balance, with routines that support long-term transformation and health. Steve helps clients unlock their full physical and personal potential."
        );

        userGenerator.createTrainerUser(
                "bannerb", "securePass10", "Bruce", "Banner", "bruce.banner@example.com", "0912345680",
                "Bruce blends the science of human performance with practical fitness techniques. With a background in biomechanics and kinesiology, he focuses on smart training that maximizes results while minimizing injury risk. His calm demeanor and methodical approach help clients understand the 'why' behind every movement. Bruce believes in evidence-based training, recovery, and a well-rounded program that supports both the body and the mind in harmony."
        );
    }

    public void createUsers() {
        userGenerator.creatingUser("bwilliams", "securePass3", "Bob", "Williams", "bob.williams@example.com", "0912345673");
        userGenerator.creatingUser("cjohnson", "securePass4", "Carol", "Johnson", "carol.johnson@example.com", "0912345674");
        userGenerator.creatingUser("dmiller", "securePass5", "David", "Miller", "david.miller@example.com", "0912345675");
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


}
