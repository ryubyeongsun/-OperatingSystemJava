import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.border.EmptyBorder;
import java.sql.DatabaseMetaData;




// DB 클래스는 JFrame을 상속받아 수강신청 창을 나타냅니다.
    public class DB extends JFrame {
        private Connection conn;  // 데이터베이스 연결을 위한 Connection 객체

    // 학생 정보 입력을 위한 텍스트 필드들
        private JTextField studentIdField;
        private JTextField studentNameField;
        private JTextField studentAgeField;
        private JTextField studentAddressField;
     // 수강 정보 입력을 위한 텍스트 필드들
        private JTextField courseCodeField;
        private JTextField courseNameField;
        private JTextField instructorField;
    // 강의 정보 입력을 위한 텍스트 필드들
        private JTextField subjectCodeField;
        private JTextField subjectNameField;
        private JTextField instructorSubjectField;



    // DB 클래스의 생성자
        public DB() {
            setTitle("수강신청 창"); // 프레임 제목 설정
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 프레임을 닫을 때 프로그램 종료

            // 전체 프레임에 BorderLayout 설정
            setLayout(new BorderLayout());

            // 학생 정보 패널
            JPanel studentPanel = createPanel("학생 ID:", "이름:", "나이:", "주소:");
            // 학생 정보 버튼 패널 생성
            JPanel studentButtonPanel = createButtonPanel("검색", "추가", "삭제", "변경");
            // 학생 정보 패널과 버튼 패널을 합쳐 학생 정보 패널 생성
            JPanel studentInfoPanel = createInfoPanel("학생 정보", studentPanel, studentButtonPanel);

            // 수강 정보 패널
            JPanel coursePanel = createPanel("학생 ID:", "이름", "과목아이디:", "점수:");
            // 수강 정보 버튼 패널 생성
            JPanel courseButtonPanel = createButtonPanel2("검색", "추가", "삭제", "변경");
            // 수강 정보 패널과 버튼 패널을 합쳐 수강 정보 패널 생성
            JPanel courseInfoPanel = createInfoPanel("수강 정보", coursePanel, courseButtonPanel);

            // 강의 정보 패널
            JPanel subjectPanel = createPanel("과목아이디:", "강의명:", "담당 교수:", "학년");
            // 강의 정보 버튼 패널 생성
            JPanel subjectButtonPanel = createButtonPanel3("검색", "추가", "삭제", "변경");
            // 수강 정보 패널과 버튼 패널을 합쳐 수강 정보 패널 생성
            JPanel subjectInfoPanel = createInfoPanel("강의 정보", subjectPanel, subjectButtonPanel);


            // 전체 창에 정보 패널들을 가운데 정렬하여 추가
            JPanel centerPanel = new JPanel(new GridLayout(3, 1, 10, 10));
            centerPanel.add(studentInfoPanel);
            centerPanel.add(courseInfoPanel);
            centerPanel.add(subjectInfoPanel);
            add(centerPanel, BorderLayout.CENTER);

            setSize(800, 600);
            setLocationRelativeTo(null); // 화면 중앙에 표시
            setVisible(true);
            //데이터베이스 연결
            connectToDatabase();


        }





    // 학생, 수강, 강의 정보를 입력받는 패널 생성
        private JPanel createPanel(String label1, String label2, String label3, String label4) {
            JPanel panel = new JPanel(new GridLayout(2, 4, 10, 10));// 패널 생성 및 GridLayout 설정 (2행 4열)
             // 패널을 나누는 라인 추가
            JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
            separator.setPreferredSize(new Dimension(0, 2));
            panel.add(separator);
            // 각각의 입력 필드에 대한 라벨 추가
            JLabel labelField1 = new JLabel(label1);
            JLabel labelField2 = new JLabel(label2);
            JLabel labelField3 = new JLabel(label3);
            JLabel labelField4 = new JLabel(label4);
            // 대학명 라벨 추가
            JLabel greetingLabel = new JLabel("Chosun University");
            greetingLabel.setForeground(Color.BLUE);
            greetingLabel.setFont(new Font("SansSerif", Font.BOLD, 31));
            greetingLabel.setHorizontalAlignment(JLabel.CENTER);
            greetingLabel.setBorder(BorderFactory.createEmptyBorder(50, 0, 20, 60));
            panel.add(greetingLabel);

            // 대학 로고 이미지 추가 및 크기 조정
            ImageIcon icon = new ImageIcon("/Users/ryubyeongseon/Desktop/다운로드.png");
            Image image = icon.getImage().getScaledInstance(200, 100, Image.SCALE_SMOOTH); // 적절한 크기로 조정
            ImageIcon scaledIcon = new ImageIcon(image);

            JLabel imageLabel = new JLabel(scaledIcon);
            imageLabel.setHorizontalAlignment(JLabel.CENTER);
            panel.add(imageLabel);

            return panel;
        }




        private JPanel createButtonPanel(String label1, String label2, String label3, String label4) {
            JPanel panel = new JPanel(new GridLayout(1, 4));// 패널 생성 및 GridLayout 설정 (1행 4열)
            // 버튼들 생성
            JButton searchButton = new JButton(label1);
            JButton addButton = new JButton(label2);
            JButton deleteButton = new JButton(label3);
            JButton updateButton = new JButton(label4);
            // 버튼에 대한 액션 리스너 등록
            searchButton.addActionListener(e -> searchStudent());
            addButton.addActionListener(e -> addStudent());
            deleteButton.addActionListener(e -> deleteStudent());
            updateButton.addActionListener(e -> updateStudent());


            // 버튼들을 패널에 추가
            panel.add(searchButton);
            panel.add(addButton);
            panel.add(deleteButton);
            panel.add(updateButton);

            return panel;
        }
        private JPanel createButtonPanel2(String label1, String label2, String label3, String label4) {
            JPanel panel = new JPanel(new GridLayout(1, 4));// 패널 생성 및 GridLayout 설정 (1행 4열)
            // 버튼들 생성
            JButton searchButton = new JButton(label1);
            JButton addButton = new JButton(label2);
            JButton deleteButton = new JButton(label3);
            JButton updateButton = new JButton(label4);

            // 버튼에 대한 액션 리스너 등록
            searchButton.addActionListener(e -> searchEnrollment());
            addButton.addActionListener(e -> addEnrollment());
            deleteButton.addActionListener(e -> deleteEnrollment());
            updateButton.addActionListener(e -> updateEnrollment());
            // 버튼들을 패널에 추가
            panel.add(searchButton);
            panel.add(addButton);
            panel.add(deleteButton);
            panel.add(updateButton);

            return panel;
        }
        private JPanel createButtonPanel3(String label1, String label2, String label3, String label4) {
            JPanel panel = new JPanel(new GridLayout(1, 4));// 패널 생성 및 GridLayout 설정 (1행 4열)
            // 버튼들 생성
            JButton searchButton = new JButton(label1);
            JButton addButton = new JButton(label2);
            JButton deleteButton = new JButton(label3);
            JButton updateButton = new JButton(label4);
            // 버튼에 대한 액션 리스너 등록

            searchButton.addActionListener(e-> searchSubject() );
            addButton.addActionListener(e -> addSubject());
            deleteButton.addActionListener(e -> deleteSubject());
            updateButton.addActionListener(e -> updateSubject());
            // 버튼들을 패널에 추가
            panel.add(searchButton);
            panel.add(addButton);
            panel.add(deleteButton);
            panel.add(updateButton);

            return panel;
        }



        private JPanel createInfoPanel(String title, JPanel infoPanel, JPanel buttonPanel) {
            JPanel panel = new JPanel(new BorderLayout());// 패널 생성 및 BorderLayout 설정
            panel.setBorder(BorderFactory.createTitledBorder(title)); // 패널에 경계선과 제목(title) 추가
            // 정보 패널과 버튼 패널을 BorderLayout의 CENTER와 SOUTH에 추가
            panel.add(infoPanel, BorderLayout.CENTER);
            panel.add(buttonPanel, BorderLayout.SOUTH);
            return panel;
        }


    private void connectToDatabase() {
        try {
            // Oracle JDBC 드라이버 클래스 로드
            Class.forName("oracle.jdbc.driver.OracleDriver");
            // 데이터베이스에 연결
            // jdbc:oracle:thin:@localhost:1521:FREE -> "jdbc:oracle:thin:@호스트주소:포트번호:데이터베이스SID"
            // "htetmyet" -> 데이터베이스 사용자명
            // "1234" -> 데이터베이스 비밀번호
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:FREE", "htetmyet", "1234");
            System.out.print("연결성공");
        } catch (ClassNotFoundException | SQLException e) {
            // 드라이버 클래스 또는 연결 예외가 발생한 경우 예외 처리
            e.printStackTrace();
            // 에러 다이얼로그 표시
            JOptionPane.showMessageDialog(this, "데이터베이스 연결 실패", "에러", JOptionPane.ERROR_MESSAGE);
            System.exit(1);// 프로그램 종료
        }
    }

        private void searchStudent() {
            // 데이터베이스에서 전체 학생 정보를 가져오는 로직을 작성
            String query = "SELECT * FROM Students";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                ResultSet resultSet = pstmt.executeQuery();

                // 검색된 결과를 화면에 표시하는 로직
                StringBuilder resultText = new StringBuilder();
                while (resultSet.next()) {
                    String studentId = resultSet.getString("student_id");
                    String studentName = resultSet.getString("student_name");
                    String studentAge = resultSet.getString("student_age");
                    String studentAddress = resultSet.getString("student_address");

                    // 가져온 정보를 resultText에 추가
                    resultText.append("학생 ID: ").append(studentId).append(", ")
                            .append("이름: ").append(studentName).append(", ")
                            .append("나이: ").append(studentAge).append(", ")
                            .append("주소: ").append(studentAddress).append("\n");
                }

                if (resultText.length() > 0) {
                    // 검색 결과가 있을 때만 결과를 다이얼로그에 표시
                    showResultDialog(resultText.toString());
                } else {
                    // 검색 결과가 없을 경우 메시지 표시
                    JOptionPane.showMessageDialog(this, "검색된 학생 정보가 없습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException e) {// SQL 예외 처리
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "학생 정보 검색 실패", "에러", JOptionPane.ERROR_MESSAGE);
            }
        }

    //검색 결과를 표시하는 다이얼로그를 생성하는 메서드
        private void showResultDialog(String resultText) {
            // 검색 결과를 표시하는 다이얼로그 생성
            JDialog resultDialog = new JDialog(this, "검색 결과", true);
            resultDialog.setLayout(new BorderLayout());

            // 결과를 표시할 텍스트 에리아
            JTextArea resultTextArea = new JTextArea(resultText);
            resultTextArea.setEditable(false);

            // 스크롤 가능하도록 JScrollPane에 추가
            JScrollPane scrollPane = new JScrollPane(resultTextArea);
            resultDialog.add(scrollPane, BorderLayout.CENTER);

            // 닫기 버튼
            JButton closeButton = new JButton("닫기");
            closeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // 닫기 버튼이 눌렸을 때의 로직
                    resultDialog.dispose();
                }
            });

            // 닫기 버튼을 담을 패널
            JPanel closeButtonPanel = new JPanel();
            closeButtonPanel.add(closeButton);

            resultDialog.add(closeButtonPanel, BorderLayout.SOUTH);
            // 다이얼로그 크기 및 표시 설정
            resultDialog.setSize(400, 300);
            resultDialog.setVisible(true);
        }

        private void addStudent() {
            // 다이얼로그 생성
            JDialog addStudentDialog = new JDialog(this, "학생 추가", true);
            addStudentDialog.setLayout(new GridLayout(5, 2));
            // 각 필드에 대한 라벨과 텍스트 필드 추가
            addStudentDialog.add(new JLabel("학생 ID:"));
            JTextField studentIdFieldDialog = new JTextField();
            addStudentDialog.add(studentIdFieldDialog);

            addStudentDialog.add(new JLabel("이름:"));
            JTextField studentNameFieldDialog = new JTextField();
            addStudentDialog.add(studentNameFieldDialog);

            addStudentDialog.add(new JLabel("나이:"));
            JTextField studentAgeFieldDialog = new JTextField();
            addStudentDialog.add(studentAgeFieldDialog);

            addStudentDialog.add(new JLabel("주소:"));
            JTextField studentAddressFieldDialog = new JTextField();
            addStudentDialog.add(studentAddressFieldDialog);
            // 추가 및 취소 버튼 생성
            JButton addButtonDialog = new JButton("추가");
            JButton cancelButtonDialog = new JButton("취소");

            addStudentDialog.add(addButtonDialog);
            addStudentDialog.add(cancelButtonDialog);
            // 추가 버튼 리스너 설정
            addButtonDialog.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // 추가 버튼이 눌렸을 때의 로직
                    String studentId = studentIdFieldDialog.getText();
                    String studentName = studentNameFieldDialog.getText();
                    String studentAge = studentAgeFieldDialog.getText();
                    String studentAddress = studentAddressFieldDialog.getText();


                    try {// SQL 쿼리 작성
                        //데이터베이스 테이블인 "Students"에 새로운 학생 정보를 추가하기 위한 SQL 쿼리를 문자열로 정의
                        String query = "INSERT INTO Students (student_id, student_name, student_age, student_address) VALUES (?, ?, ?, ?)";//실제로 삽입될 값의 목록을 지정
                        PreparedStatement pstmt = conn.prepareStatement(query);
                        pstmt.setString(1, studentId);
                        pstmt.setString(2, studentName);
                        pstmt.setString(3, studentAge);
                        pstmt.setString(4, studentAddress);

                        int affectedRows = pstmt.executeUpdate();// 쿼리 실행 및 영향 받은 행 수 확인

                        // 결과를 확인하고 적절한 메시지를 표시합니다.
                        if (affectedRows > 0) {
                            JOptionPane.showMessageDialog(addStudentDialog, "학생 정보가 추가되었습니다.", "성공", JOptionPane.INFORMATION_MESSAGE);
                            // 커밋 추가
                            conn.commit();
                        } else {
                            JOptionPane.showMessageDialog(addStudentDialog, "학생 정보 추가에 실패했습니다.", "에러", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (SQLException ex) {// SQL 예외 처리
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(addStudentDialog, "데이터베이스 오류: " + ex.getMessage(), "에러", JOptionPane.ERROR_MESSAGE);
                    }

                    // 추가가 성공했다고 가정하면
                    addStudentDialog.dispose();
                }
            });
            // 취소 버튼 리스너 설정
            cancelButtonDialog.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // 취소 버튼이 눌렸을 때의 로직
                    addStudentDialog.dispose();
                }
            });

            // 다이얼로그 크기 및 표시 설정
            addStudentDialog.setSize(300, 200);
            addStudentDialog.setVisible(true);
        }


        private void deleteStudent() {
            // 학생 정보 삭제 창 생성
            JDialog deleteStudentDialog = new JDialog(this, "학생 삭제", true);
            deleteStudentDialog.setLayout(new GridLayout(2, 2));
            // 학생 ID 입력 필드 및 라벨 추가
            deleteStudentDialog.add(new JLabel("학생 ID:"));
            JTextField studentIdFieldDialog = new JTextField();
            deleteStudentDialog.add(studentIdFieldDialog);
            // 삭제 및 취소 버튼 생성
            JButton deleteButtonDialog = new JButton("삭제");
            JButton cancelButtonDialog = new JButton("취소");

            deleteStudentDialog.add(deleteButtonDialog);
            deleteStudentDialog.add(cancelButtonDialog);
            // 삭제 버튼 리스너 설정
            deleteButtonDialog.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // 삭제 버튼이 눌렸을 때의 로직
                    String studentId = studentIdFieldDialog.getText();


                    // 삭제 쿼리 작성
                    String query = "DELETE FROM Students WHERE student_id = ?";
                    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                        pstmt.setString(1, studentId);
                        // 쿼리 실행 및 영향을 받은 행 수 확인
                        int affectedRows = pstmt.executeUpdate();
                        if (affectedRows > 0) {// 삭제 성공 시 메시지 표시
                            JOptionPane.showMessageDialog(deleteStudentDialog, "학생 정보 삭제 성공", "알림", JOptionPane.INFORMATION_MESSAGE);
                        } else {// 삭제 실패 시 메시지 표시
                            JOptionPane.showMessageDialog(deleteStudentDialog, "학생 정보 삭제 실패", "에러", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();// 예외 발생 시 메시지 표시
                        JOptionPane.showMessageDialog(deleteStudentDialog, "학생 정보 삭제 실패", "에러", JOptionPane.ERROR_MESSAGE);
                    }

                    // 삭제가 성공했다고 가정하면 다이얼로그를 닫습니다.
                    deleteStudentDialog.dispose();
                }
            });
            // 취소 버튼 리스너 설정
            cancelButtonDialog.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // 취소 버튼이 눌렸을 때의 로직
                    deleteStudentDialog.dispose();
                }
            });
            // 다이얼로그 크기 및 표시 설정
            deleteStudentDialog.setSize(300, 100);
            deleteStudentDialog.setVisible(true);
        }
        private void updateStudent() {
            // 학생 정보 변경 코드 작성
            // 사용자로부터 학생 ID, 이름, 나이, 주소를 입력받아 해당 학생의 정보를 데이터베이스에서 수정
            String studentId = JOptionPane.showInputDialog(this, "변경할 학생의 ID를 입력하세요:");
            if (studentId != null && !studentId.trim().isEmpty()) {
                // 입력 받은 학생 ID에 해당하는 정보를 검색하는 쿼리 작성
                String query = "SELECT * FROM Students WHERE student_id = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                    pstmt.setString(1, studentId);
                    ResultSet resultSet = pstmt.executeQuery();

                    if (resultSet.next()) {
                        // 입력 받은 정보로 업데이트
                        String newStudentName = JOptionPane.showInputDialog(this, "변경할 학생의 이름을 입력하세요:", resultSet.getString("student_name"));
                        String newStudentAge = JOptionPane.showInputDialog(this, "변경할 학생의 나이를 입력하세요:", resultSet.getString("student_age"));
                        String newStudentAddress = JOptionPane.showInputDialog(this, "변경할 학생의 주소를 입력하세요:", resultSet.getString("student_address"));
                        // 사용자가 모든 필드에 유효한 값을 입력했을 경우
                        if (newStudentName != null && !newStudentName.trim().isEmpty() &&
                                newStudentAge != null && !newStudentAge.trim().isEmpty() &&
                                newStudentAddress != null && !newStudentAddress.trim().isEmpty()) {
                            // 학생 정보를 업데이트하는 쿼리 작성
                            String updateQuery = "UPDATE Students SET student_name = ?, student_age = ?, student_address = ? WHERE student_id = ?";
                            try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                                // 파라미터 설정
                                updateStmt.setString(1, newStudentName);
                                updateStmt.setString(2, newStudentAge);
                                updateStmt.setString(3, newStudentAddress);
                                updateStmt.setString(4, studentId);
                                // 업데이트 실행 및 영향을 받은 행 수 확인
                                int affectedRows = updateStmt.executeUpdate();
                                if (affectedRows > 0) {// 업데이트 성공 시 메시지 표시
                                    JOptionPane.showMessageDialog(this, "학생 정보 변경 성공", "알림", JOptionPane.INFORMATION_MESSAGE);
                                } else {// 업데이트 실패 시 메시지 표시
                                    JOptionPane.showMessageDialog(this, "학생 정보 변경 실패", "에러", JOptionPane.ERROR_MESSAGE);
                                }
                            } catch (SQLException e) {// 예외 발생 시 메시지 표시
                                e.printStackTrace();
                                JOptionPane.showMessageDialog(this, "학생 정보 변경 실패", "에러", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {// 사용자가 필수 정보 중 하나 이상을 입력하지 않은 경우
                            JOptionPane.showMessageDialog(this, "모든 정보를 입력하세요.", "경고", JOptionPane.WARNING_MESSAGE);
                        }
                    } else {// 학생 ID가 존재하지 않을 경우 경고 메시지 표시
                        JOptionPane.showMessageDialog(this, "입력한 학생 ID가 존재하지 않습니다.", "에러", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException e) {// 예외 발생 시 메시지 표시
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "학생 정보 변경 실패", "에러", JOptionPane.ERROR_MESSAGE);
                }
            } else {// 학생 ID를 입력하지 않았을 경우 경고 메시지 표시
                JOptionPane.showMessageDialog(this, "학생 ID를 입력하세요.", "경고", JOptionPane.WARNING_MESSAGE);
            }
        }

        private void searchEnrollment() {
            // 데이터베이스에서 전체 수강 정보를 가져오는 로직을 작성
            String query = "SELECT * FROM Enrollment";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                ResultSet resultSet = pstmt.executeQuery();

                // 검색된 결과를 화면에 표시하는 로직
                StringBuilder resultText = new StringBuilder();
                while (resultSet.next()) {
                    String studentId = resultSet.getString("student_id");
                    String subjectId = resultSet.getString("subject_id");
                    String score = resultSet.getString("score");

                    // 가져온 정보를 resultText에 추가
                    resultText.append("학생 ID: ").append(studentId).append(", ")
                            .append("과목 ID: ").append(subjectId).append(", ")
                            .append("점수: ").append(score).append("\n");
                }

                // 검색 결과를 다이얼로그에 표시
                showResultDialog(resultText.toString());
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "수강 정보 검색 실패", "에러", JOptionPane.ERROR_MESSAGE);
            }
        }


        private void showResultDialog2(String resultText) {
            // 검색 결과를 표시하는 다이얼로그 생성
            JDialog resultDialog = new JDialog(this, "검색 결과", true);
            resultDialog.setLayout(new BorderLayout());

            // 결과를 표시할 텍스트 에리아
            JTextArea resultTextArea = new JTextArea(resultText);
            resultTextArea.setEditable(false);

            // 스크롤 가능하도록 JScrollPane에 추가
            JScrollPane scrollPane = new JScrollPane(resultTextArea);
            resultDialog.add(scrollPane, BorderLayout.CENTER);

            // 닫기 버튼
            JButton closeButton = new JButton("닫기");
            closeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // 닫기 버튼이 눌렸을 때의 로직
                    resultDialog.dispose();
                }
            });

            // 닫기 버튼을 담을 패널
            JPanel closeButtonPanel = new JPanel();
            closeButtonPanel.add(closeButton);

            resultDialog.add(closeButtonPanel, BorderLayout.SOUTH);

            resultDialog.setSize(400, 300);
            resultDialog.setVisible(true);
        }


        private void addEnrollment() {
            // 수강 정보 추가 코드 작성
            // 사용자로부터 입력을 받아 데이터베이스에 새로운 수강 정보 추가
            String studentId = JOptionPane.showInputDialog(this, "학생 ID를 입력하세요:");
            String subjectId = JOptionPane.showInputDialog(this, "과목 ID를 입력하세요:");
            String score = JOptionPane.showInputDialog(this, "점수를 입력하세요:");
            // 사용자가 모든 필드에 유효한 값을 입력했을 경우
            if (studentId != null && !studentId.trim().isEmpty() &&
                    subjectId != null && !subjectId.trim().isEmpty() &&
                    score != null && !score.trim().isEmpty()) {
                // 수강 정보를 추가하는 쿼리 작성
                String query = "INSERT INTO Enrollment VALUES (?, ?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                    pstmt.setString(1, studentId);
                    pstmt.setString(2, subjectId);
                    pstmt.setString(3, score);
                    // 쿼리 실행 및 결과 처리
                    int affectedRows = pstmt.executeUpdate();
                    if (affectedRows > 0) {
                        JOptionPane.showMessageDialog(this, "수강 정보 추가 성공", "알림", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "수강 정보 추가 실패", "에러", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "수강 정보 추가 실패", "에러", JOptionPane.ERROR_MESSAGE);
                }
            } else {// 사용자가 필수 정보 중 하나 이상을 입력하지 않은 경우
                JOptionPane.showMessageDialog(this, "모든 정보를 입력하세요.", "경고", JOptionPane.WARNING_MESSAGE);
            }
        }
        private void deleteEnrollment() {
            // 사용자로부터 학생 아이디를 입력 받습니다.
            String studentId = JOptionPane.showInputDialog(this, "삭제할 학생의 아이디를 입력하세요:");

            // 학생 아이디가 입력되었는지 확인합니다.
            if (studentId != null && !studentId.trim().isEmpty()) {
                // 삭제 여부를 사용자에게 확인하는 다이얼로그를 표시합니다.
                int option = JOptionPane.showConfirmDialog(this, "정말로 삭제하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION);
                // 사용자가 확인을 선택한 경우에만 삭제 작업을 수행합니다.
                if (option == JOptionPane.YES_OPTION) {
                    // 학생 아이디에 해당하는 모든 수강 정보를 삭제하는 쿼리를 작성하고 실행합니다.
                    String query = "DELETE FROM Enrollment WHERE student_id = ?";
                    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                        pstmt.setString(1, studentId);
                        // 쿼리 실행 및 결과 처리
                        int affectedRows = pstmt.executeUpdate();
                        if (affectedRows > 0) {
                            JOptionPane.showMessageDialog(this, "수강 정보 삭제 성공", "알림", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(this, "해당 학생의 수강 정보가 없습니다.", "에러", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(this, "수강 정보 삭제 실패", "에러", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "학생 아이디를 입력하세요.", "경고", JOptionPane.WARNING_MESSAGE);
            }
        }



        private void updateEnrollment() {
            // 사용자로부터 학생 ID, 과목 ID, 점수를 입력 받습니다.
            String studentId = JOptionPane.showInputDialog(this, "수정할 학생 ID를 입력하세요:");
            String subjectId = JOptionPane.showInputDialog(this, "수정할 과목 ID를 입력하세요:");
            String newScore = JOptionPane.showInputDialog(this, "새로운 점수를 입력하세요:");

            try {
                // 과목 정보를 업데이트하기 위한 SQL 쿼리를 준비하고 실행합니다.
                String query = "UPDATE Enrollment SET score = ? WHERE student_id = ? AND subject_id = ?";
                PreparedStatement pstmt = conn.prepareStatement(query);

                pstmt.setString(1, newScore);
                pstmt.setString(2, studentId);
                pstmt.setString(3, subjectId);
                // 쿼리 실행 및 결과 처리
                int rowsUpdated = pstmt.executeUpdate();

                // 업데이트가 성공적으로 이루어졌는지 확인하고 적절한 메시지를 표시합니다.
                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(this, "수강 정보가 성공적으로 변경되었습니다.");
                } else {
                    JOptionPane.showMessageDialog(this, "수강 정보 변경에 실패했습니다. 해당 학생 ID와 과목 ID를 확인하세요.", "에러", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "데이터베이스 오류: " + e.getMessage(), "에러", JOptionPane.ERROR_MESSAGE);
            }
        }


        private void searchSubject() {
            // 과목 정보 검색 코드 작성
            // 데이터베이스에서 전체 과목 정보를 조회하여 화면에 표시
            String query = "SELECT * FROM Subjects";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                ResultSet resultSet = pstmt.executeQuery();

                // 여기서 결과를 화면에 표시하는 로직을 추가하면 됩니다.
                // resultSet를 이용하여 필요한 정보를 가져와서 출력 또는 화면에 표시
                StringBuilder resultText = new StringBuilder();
                while (resultSet.next()) {
                    int subjectId = resultSet.getInt("subject_id");
                    String title = resultSet.getString("title");
                    String professor = resultSet.getString("professor");
                    int grade = resultSet.getInt("grade");

                    // 가져온 정보를 resultText에 추가
                    resultText.append("과목 ID: ").append(subjectId).append(", ")
                            .append("과목명: ").append(title).append(", ")
                            .append("담당 교수: ").append(professor).append(", ")
                            .append("학년: ").append(grade).append("\n");
                }

                // 검색 결과를 다이얼로그에 표시
                showResultDialog(resultText.toString(), "과목 정보 검색 결과");
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "과목 정보 검색 실패", "에러", JOptionPane.ERROR_MESSAGE);
            }
        }


        // 검색 결과를 표시하는 다이얼로그 메소드
        private void showResultDialog(String resultText, String title) {
            // 결과를 표시하는 다이얼로그 생성
            JDialog resultDialog = new JDialog(this, title, true);
            resultDialog.setLayout(new BorderLayout());

            // 결과를 표시할 텍스트 에리아
            JTextArea resultTextArea = new JTextArea(resultText);
            resultTextArea.setEditable(false);

            // 스크롤 가능하도록 JScrollPane에 추가
            JScrollPane scrollPane = new JScrollPane(resultTextArea);
            resultDialog.add(scrollPane, BorderLayout.CENTER);

            // 닫기 버튼
            JButton closeButton = new JButton("닫기");
            closeButton.addActionListener(e -> resultDialog.dispose());

            // 닫기 버튼을 담을 패널
            JPanel closeButtonPanel = new JPanel();
            closeButtonPanel.add(closeButton);

            resultDialog.add(closeButtonPanel, BorderLayout.SOUTH);

            resultDialog.setSize(400, 300);
            resultDialog.setVisible(true);
        }



    private void addSubject() {
            // 사용자로부터 강의 정보를 입력 받습니다.
            String subjectIdStr = JOptionPane.showInputDialog(this, "과목 아이디를 입력하세요:");
            String title = JOptionPane.showInputDialog(this, "과목명을 입력하세요:");
            String professor = JOptionPane.showInputDialog(this, "담당 교수를 입력하세요:");
            String gradeStr = JOptionPane.showInputDialog(this, "학년을 입력하세요:");

            try {
                // 강의 정보를 데이터베이스에 추가하기 위한 SQL 쿼리를 준비하고 실행합니다.
                String query = "INSERT INTO Subjects (subject_id, title, professor, grade) VALUES (?, ?, ?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(query);

                // 입력 받은 문자열을 적절한 형식으로 변환하여 설정합니다.
                pstmt.setInt(1, Integer.parseInt(subjectIdStr));
                pstmt.setString(2, title);
                pstmt.setString(3, professor);
                pstmt.setInt(4, Integer.parseInt(gradeStr));

                int affectedRows = pstmt.executeUpdate();

                // 결과를 확인하고 적절한 메시지를 표시합니다.
                if (affectedRows > 0) {
                    JOptionPane.showMessageDialog(this, "과목 정보가 추가되었습니다.", "성공", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "과목 정보 추가에 실패했습니다.", "에러", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "데이터베이스 오류: " + e.getMessage(), "에러", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "잘못된 형식의 입력입니다. 숫자를 입력하세요.", "에러", JOptionPane.ERROR_MESSAGE);
            }
        }



        private void deleteSubject() {
            // 사용자로부터 과목 아이디를 입력 받습니다.
            String subjectIdStr = JOptionPane.showInputDialog(this, "삭제할 과목의 아이디를 입력하세요:");

            try {
                // 과목 정보를 데이터베이스에서 삭제하기 위한 SQL 쿼리를 준비하고 실행합니다.
                String query = "DELETE FROM Subjects WHERE subject_id = ?";
                PreparedStatement pstmt = conn.prepareStatement(query);

                // 입력 받은 문자열을 적절한 형식으로 변환하여 설정합니다.
                pstmt.setInt(1, Integer.parseInt(subjectIdStr));

                int affectedRows = pstmt.executeUpdate();

                // 결과를 확인하고 적절한 메시지를 표시합니다.
                if (affectedRows > 0) {
                    JOptionPane.showMessageDialog(this, "과목 정보가 삭제되었습니다.", "성공", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "과목 정보 삭제에 실패했습니다. 해당 과목 아이디를 확인하세요.", "에러", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "데이터베이스 오류: " + e.getMessage(), "에러", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "잘못된 형식의 입력입니다. 숫자를 입력하세요.", "에러", JOptionPane.ERROR_MESSAGE);
            }
        }


        private void updateSubject() {
            // 사용자로부터 과목 코드, 새로운 과목명, 새로운 교수를 입력 받습니다.
            String subjectCode = JOptionPane.showInputDialog(this, "수정할 과목 코드를 입력하세요:");
            String newTitle = JOptionPane.showInputDialog(this, "새로운 과목명을 입력하세요:");
            String newProfessor = JOptionPane.showInputDialog(this, "새로운 교수를 입력하세요:");

            try {
                // 과목 정보를 데이터베이스에서 수정하기 위한 SQL 쿼리를 준비하고 실행합니다.
                String query = "UPDATE Subjects SET title = ?, professor = ? WHERE subject_id = ?";
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setString(1, newTitle);
                pstmt.setString(2, newProfessor);
                pstmt.setString(3, subjectCode);

                int affectedRows = pstmt.executeUpdate();

                // 결과를 확인하고 적절한 메시지를 표시합니다.
                if (affectedRows > 0) {
                    JOptionPane.showMessageDialog(this, "과목 정보가 성공적으로 변경되었습니다.");
                } else {
                    JOptionPane.showMessageDialog(this, "과목 정보 변경에 실패했습니다. 해당 과목 코드를 확인하세요.", "에러", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "데이터베이스 오류: " + e.getMessage(), "에러", JOptionPane.ERROR_MESSAGE);
            }
        }



        public static void main(String[] args) {
        new DB(); // DB 클래스의 인스턴스를 생성하여 어플리케이션 시작

    }
}
