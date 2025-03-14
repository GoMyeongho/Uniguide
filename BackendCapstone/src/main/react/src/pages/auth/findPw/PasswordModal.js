import React, { useState } from "react";
import styled from "styled-components";
import AuthApi from "../../../api/AuthApi";

const PasswordModal = ({ closeModal }) => {
  const [newPassword, setNewPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const [successMessage, setSuccessMessage] = useState("");

  const handleInputChange = (e, setState) => {
    setState(e.target.value);
  };

  const handlePasswordReset = async () => {
    if (newPassword !== confirmPassword) {
      setErrorMessage("비밀번호가 일치하지 않습니다.");
      return;
    }
    try {
      const response = await AuthApi.changePassword(newPassword); // 비밀번호 변경 API 호출
      if (response) {
        setSuccessMessage("비밀번호가 성공적으로 변경되었습니다.");
        setErrorMessage("");
        setTimeout(() => {
          closeModal(); // 성공 후 모달 닫기
        }, 2000);
      } else {
        setErrorMessage("비밀번호 변경에 실패했습니다.");
      }
    } catch (e) {
      console.error("오류 발생:", e);
      setErrorMessage("서버가 응답하지 않습니다.");
    }
  };

  return (
      <ModalOverlay>
        <ModalContainer>
          <h2>비밀번호 변경</h2>
          <InputContainer>
            <label>새 비밀번호</label>
            <StyledInput
                type="password"
                value={newPassword}
                onChange={(e) => handleInputChange(e, setNewPassword)}
                placeholder="새 비밀번호를 입력하세요"
            />
          </InputContainer>
          <InputContainer>
            <label>비밀번호 확인</label>
            <StyledInput
                type="password"
                value={confirmPassword}
                onChange={(e) => handleInputChange(e, setConfirmPassword)}
                placeholder="비밀번호를 확인하세요"
            />
          </InputContainer>
          {errorMessage && <ErrorMessage>{errorMessage}</ErrorMessage>}
          {successMessage && <SuccessMessage>{successMessage}</SuccessMessage>}
          <ButtonContainer>
            <StyledButton onClick={handlePasswordReset}>비밀번호 변경</StyledButton>
            <CancelButton onClick={closeModal}>취소</CancelButton>
          </ButtonContainer>
        </ModalContainer>
      </ModalOverlay>
  );
};

export default PasswordModal;

// 스타일 컴포넌트 정의
const ModalOverlay = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 9999;
`;

const ModalContainer = styled.div`
  background: white;
  width: 400px;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  text-align: center;
`;

const InputContainer = styled.div`
  margin-bottom: 15px;
  text-align: left;

  label {
    display: block;
    font-size: 14px;
    color: #333;
    margin-bottom: 5px;
  }
`;

const StyledInput = styled.input`
  width: 100%;
  padding: 12px;
  border: 2px solid #ddd;
  border-radius: 8px;
  font-size: 16px;
  transition: border-color 0.3s;

  &:focus {
    border-color: #a16eff;
    outline: none;
    box-shadow: 0 0 4px rgba(161, 110, 255, 0.5);
  }
`;

const ErrorMessage = styled.div`
  color: red;
  font-size: 14px;
  margin-bottom: 10px;
`;

const SuccessMessage = styled.div`
  color: green;
  font-size: 14px;
  margin-bottom: 10px;
`;

const ButtonContainer = styled.div`
  display: flex;
  justify-content: space-between;
  margin-top: 10px;
`;

const StyledButton = styled.button`
  background: #a16eff;
  color: white;
  border: none;
  padding: 12px;
  font-size: 16px;
  border-radius: 8px;
  cursor: pointer;
  flex: 1;
  margin-right: 5px;
  transition: background 0.3s;

  &:hover {
    background: #dccafc;
  }
`;

const CancelButton = styled(StyledButton)`
  background:  #dccafc;
  color: white;

  &:hover {
    background:  #a16eff;
  }
`;
